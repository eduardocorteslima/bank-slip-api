package com.contaazul.bankslip.rest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.Test;

import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.enums.BankslipStatus;

public class BankslipPostRestControllerTest extends RestControllerTest {
	
	
	@Test
	public void post_one_baskslip_must_return_bankslip_created() throws Exception {

		Bankslip bankslip = Bankslip.builder()
				.dueDate(LocalDate.parse("2018-01-01", FORMATTER))
				.totalInCents(BigDecimal.valueOf(100000))
				.customer("Trillian Company")
				.status(BankslipStatus.PENDING)
				.build();
				
		mockMvc.perform(post("/bankslips")
				.content(this.json(bankslip))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isCreated());
		
		List<Bankslip> list = (List<Bankslip>) bankslipRepository.findAll();
		assertThat(list.size()).isEqualTo(1);
		assertThat(list.get(0).getCustomer()).isEqualTo(bankslip.getCustomer());
		assertThat(list.get(0).getDueDate()).isEqualTo(bankslip.getDueDate());
		assertThat(list.get(0).getStatus()).isEqualTo(bankslip.getStatus());
		assertThat(list.get(0).getTotalInCents().toBigInteger()).isEqualTo(bankslip.getTotalInCents().toBigInteger());
	}
	
	@Test
	public void post_one_baskslip_must_return_bad_request() throws Exception {

		Bankslip bankslip = null;
				
		mockMvc.perform(post("/bankslips")
				.content(this.json(bankslip))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isBadRequest())		
		.andExpect(jsonPath("$.messages[0]", is("Bankslip not provided in the request body")));

	}
	
	@Test
	public void post_one_baskslip_must_return_unprocessable_entity() throws Exception {

		Bankslip bankslip = new Bankslip();
		
		mockMvc.perform(post("/bankslips")
				.content(this.json(bankslip))
				.contentType(CONTENT_TYPE))
		.andExpect(status().isUnprocessableEntity())		
		.andExpect(jsonPath("$.messages[0]", is("Invalid bankslip provided.The possible reasons are:")));

	}
	
	

}
