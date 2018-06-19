package com.contaazul.bankslip.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

import com.contaazul.bankslip.BankslipApplicationTest;
import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.enums.BankslipStatus;
import com.contaazul.bankslip.exceptions.BankslipNotFoundException;
import com.contaazul.bankslip.repository.BankslipRepository;

public class BankslipServiceTest extends BankslipApplicationTest {

	@Autowired
	private BankslipService bankslipService;

	@Autowired
	private BankslipRepository bankslipRepository;

	@Before
	public void setup() throws Exception {
		bankslipRepository.deleteAll();
	}

	@Test
	public void find_all_tests_must_return_two_records() {

		List<Bankslip> bankslips = new ArrayList<>();

		bankslips.add(bankslipRepository.save(Bankslip.builder().status(BankslipStatus.PENDING)
				.customer("Zaphod Company").totalInCents(BigDecimal.valueOf(100000)).dueDate(LocalDate.now()).build()));

		bankslips.add(bankslipRepository
				.save(Bankslip.builder().status(BankslipStatus.PENDING).customer("Ford Prefect Company")
						.totalInCents(BigDecimal.valueOf(100000)).dueDate(LocalDate.now()).build()));

		bankslipRepository.saveAll(bankslips);

		List<Bankslip> bankslipsFromService = bankslipService.findAll();

		assertThat(bankslipsFromService.size()).isEqualTo(2);

	}

	@Test
	public void find_all_tests_must_return_one_records() {

		Bankslip bankslip = bankslipRepository.save(Bankslip.builder().status(BankslipStatus.PENDING)
				.customer("Zaphod Company").totalInCents(BigDecimal.valueOf(100000)).dueDate(LocalDate.now()).build());

		bankslipRepository.save(bankslip);

		List<Bankslip> bankslipsFromService = bankslipService.findAll();

		assertThat(bankslipsFromService.size()).isEqualTo(1);
	}

	@Test
	public void find_all_tests_must_return_zero_records() {

		List<Bankslip> bankslipsFromService = bankslipService.findAll();

		assertThat(bankslipsFromService.size()).isEqualTo(0);
	}

	@Test
	public void find_by_id_must_return_one_records() {
		List<Bankslip> bankslips = new ArrayList<>();

		bankslips.add(
				bankslipRepository.save(Bankslip.builder().status(BankslipStatus.PENDING).customer("Zaphod Company")
						.totalInCents(BigDecimal.valueOf(100000.00)).dueDate(LocalDate.now()).build()));

		bankslips.add(bankslipRepository
				.save(Bankslip.builder().status(BankslipStatus.PENDING).customer("Ford Prefect Company")
						.totalInCents(BigDecimal.valueOf(100000.00)).dueDate(LocalDate.now()).build()));

		bankslipRepository.saveAll(bankslips);

		Optional<Bankslip> bankslipFromService = bankslipService.findById(bankslips.get(0).getId().toString());

		bankslips.get(0).setFine(BigDecimal.ZERO);
		validateReturnBankslip(bankslipFromService, bankslips.get(0));


	}

	@Test(expected = BankslipNotFoundException.class)
	public void find_by_id_must_return_not_found_exeption() {
	
		bankslipService.findById(UUID.randomUUID().toString());
	
	}

	@Test
	public void find_by_id_must_return_exception_invalid_id() {
		try {
			
			bankslipService.findById("1234");
			fail("Must return an exception.");
		
		} catch (IllegalArgumentException ex) {
		
			assertThat(ex.getMessage()).isEqualTo("Invalid UUID string: 1234");
		}
	}

	@Test
	public void create_one_must_persist_one() {
		Bankslip bankslip = bankslipRepository.save(Bankslip.builder().status(BankslipStatus.PENDING)
				.customer("Zaphod Company").totalInCents(BigDecimal.valueOf(100000)).dueDate(LocalDate.now()).build());

		bankslipService.createOne(bankslip);

		List<Bankslip> bankslipsFromService = (List<Bankslip>) bankslipRepository.findAll();

		assertThat(bankslipsFromService.size()).isEqualTo(1);
	}
	
	@Test(expected = DataIntegrityViolationException.class)
	public void create_one_must_persist_must_throw_exception() {
		Bankslip bankslip = bankslipRepository.save(Bankslip.builder().status(BankslipStatus.PENDING).build());
		bankslipService.createOne(bankslip);
	}


	@Test
	public void changeStatusBankslipById() {
		List<Bankslip> bankslips = new ArrayList<>();

		bankslips.add(
				bankslipRepository.save(Bankslip.builder().status(BankslipStatus.PENDING).customer("Zaphod Company")
						.totalInCents(BigDecimal.valueOf(100000.00)).dueDate(LocalDate.now()).build()));

		bankslips.add(bankslipRepository
				.save(Bankslip.builder().status(BankslipStatus.PENDING).customer("Ford Prefect Company")
						.totalInCents(BigDecimal.valueOf(100000.00)).dueDate(LocalDate.now()).build()));

		bankslipRepository.saveAll(bankslips);

		Optional<Bankslip> bankslipFromService = bankslipService.findById(bankslips.get(0).getId().toString());
	
		
		bankslips.get(0).setFine(BigDecimal.ZERO);
		validateReturnBankslip(bankslipFromService, bankslips.get(0));

		bankslips.get(0).setStatus(BankslipStatus.PENDING);
		bankslipService.changeStatusBankslipById(bankslips.get(0).getId().toString(),BankslipStatus.CANCELED);

		bankslips.get(0).setStatus(BankslipStatus.CANCELED);
		Optional<Bankslip> bankslipCanceledFromService = bankslipService.findById(bankslips.get(0).getId().toString());
		validateReturnBankslip(bankslipCanceledFromService, bankslips.get(0));
		
		
		bankslipService.changeStatusBankslipById(bankslips.get(0).getId().toString(),BankslipStatus.PAID);

		Optional<Bankslip> bankslipPaidFromService = bankslipService.findById(bankslips.get(0).getId().toString());

		bankslips.get(0).setStatus(BankslipStatus.PAID);
		validateReturnBankslip(bankslipPaidFromService, bankslips.get(0));
		
		
		Optional<Bankslip> bankslipNotChangedFromService = bankslipService.findById(bankslips.get(1).getId().toString());
		bankslips.get(1).setStatus(BankslipStatus.PENDING);
		bankslips.get(1).setFine(BigDecimal.ZERO);
		validateReturnBankslip(bankslipNotChangedFromService, bankslips.get(1));
	}

	private void validateReturnBankslip(Optional<Bankslip> returned, Bankslip expected) {
		assertThat(returned.isPresent()).isEqualTo(true);
		assertThat(returned.get().getId()).isEqualTo(expected.getId());
		assertThat(returned.get().getDueDate()).isEqualTo(expected.getDueDate());
		assertThat(returned.get().getCustomer()).isEqualTo(expected.getCustomer());
		assertThat(returned.get().getTotalInCents().toBigInteger())
				.isEqualTo(expected.getTotalInCents().toBigInteger());
		assertThat(returned.get().getStatus()).isEqualTo(expected.getStatus());
		assertThat(returned.get().getFine()).isEqualTo(expected.getFine());
	}

}
