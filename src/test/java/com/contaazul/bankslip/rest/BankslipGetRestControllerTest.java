package com.contaazul.bankslip.rest;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import org.junit.Test;

import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.enums.BankslipStatus;

public class BankslipGetRestControllerTest extends RestControllerTest {
	
	private final List<Bankslip> bankslipList = new ArrayList<>();
	
	@Test
	public void get_all_must_return_zero_baskslip() throws Exception {
		
		mockMvc.perform(get("/bankslips").contentType(CONTENT_TYPE)).andExpect(status().isOk())
		.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$", is(new ArrayList<>())));
	}

	@Test
	public void get_all_must_return_two_baskslip() throws Exception {

		bankslipList.add(bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Ford Prefect Company")
				.totalInCents(BigDecimal.valueOf(100000))
				.dueDate(LocalDate.parse("2018-01-01", FORMATTER))
				.build()));
		
		bankslipList.sort(Comparator.comparing(Bankslip::getId));
		
		System.out.println(bankslipList);
		
		mockMvc.perform(get("/bankslips").contentType(CONTENT_TYPE)).andExpect(status().isOk())
				.andExpect(content().contentType(CONTENT_TYPE))
				.andExpect(jsonPath("$[0].id", is(this.bankslipList.get(0).getId().toString())))
				.andExpect(jsonPath("$[0].due_date", is(this.bankslipList.get(0).getDueDate().toString())))
				.andExpect(jsonPath("$[0].total_in_cents", is(this.bankslipList.get(0).getTotalInCents().doubleValue())))
				.andExpect(jsonPath("$[0].customer", is(this.bankslipList.get(0).getCustomer())));
	}
	
	
	@Test
	public void get_by_id_must_return_one_baskslip_with_fine_greater_than_ten_days() throws Exception {

		Bankslip bankslip = bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Ford Prefect Company")
				.totalInCents(BigDecimal.valueOf(100000))
				.dueDate(LocalDate.parse("2018-01-01", FORMATTER))	
				.build());
		
		
		mockMvc.perform(get(String.format("/bankslips/%s", bankslip.getId()))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isOk())
		.andExpect(content().contentType(CONTENT_TYPE))
		.andExpect(jsonPath("$.id", is(bankslip.getId().toString())))
		.andExpect(jsonPath("$.due_date", is(bankslip.getDueDate().toString())))
		.andExpect(jsonPath("$.total_in_cents", is(bankslip.getTotalInCents().doubleValue())))
		.andExpect(jsonPath("$.customer", is(bankslip.getCustomer())))
		.andExpect(jsonPath("$.fine", is(BigDecimal.valueOf(1000L).doubleValue())));
	}
	
	@Test
	public void get_by_id_must_return_one_baskslip_with_fine_less_than_ten_days() throws Exception {

		Bankslip bankslip = bankslipRepository.save(Bankslip.builder()
				.status(BankslipStatus.PENDING)
				.customer("Ford Prefect Company")
				.totalInCents(BigDecimal.valueOf(100000))
				.dueDate(LocalDate.now().minusDays(6))	
				.build());
		
		
		mockMvc.perform(get(String.format("/bankslips/%s", bankslip.getId()))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isOk())
		.andExpect(content().contentType(CONTENT_TYPE))
		.andExpect(jsonPath("$.id", is(bankslip.getId().toString())))
		.andExpect(jsonPath("$.due_date", is(bankslip.getDueDate().toString())))
		.andExpect(jsonPath("$.total_in_cents", is(bankslip.getTotalInCents().doubleValue())))
		.andExpect(jsonPath("$.customer", is(bankslip.getCustomer())))
		.andExpect(jsonPath("$.fine", is(BigDecimal.valueOf(500L).doubleValue())));
	}
	
	
	@Test
	public void get_by_id_must_return_bad_request() throws Exception {
	
		mockMvc.perform(get(String.format("/bankslips/%s", "1234"))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isBadRequest())
		.andExpect(content().contentType(CONTENT_TYPE))		
		.andExpect(jsonPath("$.messages[0]", is("Invalid id provided - it must be a valid UUID")));
	}
	
	@Test
	public void get_by_id_must_return_not_found() throws Exception {
	
		mockMvc.perform(get(String.format("/bankslips/%s", UUID.randomUUID().toString()))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isNotFound())
		.andExpect(content().contentType(CONTENT_TYPE))		
		.andExpect(jsonPath("$.messages[0]", is("Bankslip not found with the specified id")));
	}

}
