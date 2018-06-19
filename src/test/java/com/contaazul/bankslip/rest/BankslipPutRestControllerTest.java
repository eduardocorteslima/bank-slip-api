package com.contaazul.bankslip.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.Test;

import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.enums.BankslipStatus;

public class BankslipPutRestControllerTest extends RestControllerTest {
	
	private static final String INVALID_ID_PROVIDED_IT_MUST_BE_A_VALID_UUID = "Invalid id provided - it must be a valid UUID";
	private static final String BANKSLIP_NOT_FOUND_WITH_THE_SPECIFIED_ID = "Bankslip not found with the specified id";
	
	private final List<Bankslip> bankslipList = new ArrayList<>();
	
	@Test
	public void pay_baskslip_must_return_paid() throws Exception {
	
		bankslipList.add(bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Ford Prefect Company")
				.totalInCents(BigDecimal.valueOf(100000))
				.dueDate(LocalDate.parse("2018-01-01", FORMATTER))
				.build()));
		
		
		bankslipList.add(bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Zaphod Company")
				.totalInCents(BigDecimal.valueOf(200000))
				.dueDate(LocalDate.parse("2018-02-01", FORMATTER))
				.build()));
	
		this.bankslipRepository.saveAll(bankslipList);
		
		Bankslip body = Bankslip.builder().status(BankslipStatus.PAID).build();
		
		mockMvc.perform(put(String.format("/bankslips/%s",bankslipList.get(0).getId().toString()))
				.content(json(body))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isOk());
		
		Optional<Bankslip> bankslipPaid= this.bankslipRepository.findById(bankslipList.get(0).getId());
		assertThat(bankslipPaid.isPresent()).isEqualTo(true);
		assertThat(bankslipPaid.get().getStatus()).isEqualTo(BankslipStatus.PAID);
	
		Optional<Bankslip> bankslipPending= this.bankslipRepository.findById(bankslipList.get(1).getId());
		assertThat(bankslipPending.isPresent()).isEqualTo(true);
		assertThat(bankslipPending.get().getStatus()).isEqualTo(BankslipStatus.PENDING);
	}
	
	@Test
	public void pay_baskslip_must_return_not_found() throws Exception {
	
		bankslipList.add(bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Ford Prefect Company")
				.totalInCents(BigDecimal.valueOf(100000))
				.dueDate(LocalDate.parse("2018-01-01", FORMATTER))
				.build()));
		
		this.bankslipRepository.saveAll(bankslipList);
		
		Bankslip body = Bankslip.builder().status(BankslipStatus.PAID).build();
		
		mockMvc.perform(put(String.format("/bankslips/%s",UUID.randomUUID().toString()))
				.content(json(body))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isNotFound())		
		.andExpect(jsonPath("$.messages[0]", is(BANKSLIP_NOT_FOUND_WITH_THE_SPECIFIED_ID)));
	}
	
	
	@Test
	public void pay_baskslip_must_return_exception() throws Exception {
	
		Bankslip body = Bankslip.builder().status(BankslipStatus.PAID).build();
		
		mockMvc.perform(put(String.format("/bankslips/%s","1234"))
				.content(json(body))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isBadRequest())		
		.andExpect(jsonPath("$.messages[0]", is(INVALID_ID_PROVIDED_IT_MUST_BE_A_VALID_UUID)));
	}
	
	@Test
	public void cancel_baskslip_must_return_canceled() throws Exception {
	
		bankslipList.add(bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Ford Prefect Company")
				.totalInCents(BigDecimal.valueOf(100000))
				.dueDate(LocalDate.parse("2018-01-01", FORMATTER))
				.build()));
		
		
		bankslipList.add(bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Zaphod Company")
				.totalInCents(BigDecimal.valueOf(200000))
				.dueDate(LocalDate.parse("2018-02-01", FORMATTER))
				.build()));
	
		this.bankslipRepository.saveAll(bankslipList);
		
		Bankslip body = Bankslip.builder().status(BankslipStatus.CANCELED).build();
		
		mockMvc.perform(put(String.format("/bankslips/%s",bankslipList.get(0).getId().toString()))
				.content(json(body))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isOk());
		
		Optional<Bankslip> bankslipPaid= this.bankslipRepository.findById(bankslipList.get(0).getId());
		assertThat(bankslipPaid.isPresent()).isEqualTo(true);;
		assertThat(bankslipPaid.get().getStatus()).isEqualTo(BankslipStatus.CANCELED);
	
		Optional<Bankslip> bankslipPending= this.bankslipRepository.findById(bankslipList.get(1).getId());
		assertThat(bankslipPending.isPresent()).isEqualTo(true);;
		assertThat(bankslipPending.get().getStatus()).isEqualTo(BankslipStatus.PENDING);
	}
	
	@Test
	public void cancel_baskslip_must_return_not_found() throws Exception {
	
		bankslipList.add(bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Ford Prefect Company")
				.totalInCents(BigDecimal.valueOf(100000))
				.dueDate(LocalDate.parse("2018-01-01", FORMATTER))
				.build()));
		
		this.bankslipRepository.saveAll(bankslipList);
		
		Bankslip body = Bankslip.builder().status(BankslipStatus.CANCELED).build();
		
		mockMvc.perform(put(String.format("/bankslips/%s",UUID.randomUUID().toString()))
				.content(json(body))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isNotFound())		
		.andExpect(jsonPath("$.messages[0]", is(BANKSLIP_NOT_FOUND_WITH_THE_SPECIFIED_ID)));
	}

}