package com.contaazul.bankslip.business;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.contaazul.bankslip.BankslipApplicationTest;

public class FineCalculatorTest extends BankslipApplicationTest {

	@Autowired
	FineCalculator fineCalculator;

	@Test
	public void test_calculating_of_fine_rate_greater_than_ten_days() {
		final long days = 11;
		final BigDecimal value = BigDecimal.valueOf(100000L);
		final BigDecimal expected = BigDecimal.valueOf(1000.0);

		BigDecimal fine = fineCalculator.calculateFine(value, LocalDate.now().minusDays(days));
		assertThat(fine).isEqualTo(expected);
	}
	
	
	@Test
	public void test_calculating_of_fine_rate_less_than_ten_days() {
		final long days = 9;
		final BigDecimal value = BigDecimal.valueOf(100000L);
		final BigDecimal expected = BigDecimal.valueOf(500.0);

		BigDecimal fine = fineCalculator.calculateFine(value, LocalDate.now().minusDays(days));
		assertThat(fine).isEqualTo(expected);
	}
	
	@Test
	public void test_calculating_of_fine_rate_zero_days() {
		final BigDecimal value = BigDecimal.valueOf(100000L);
		final BigDecimal expected = BigDecimal.valueOf(0);

		BigDecimal fine = fineCalculator.calculateFine(value, LocalDate.now());
		assertThat(fine).isEqualTo(expected);
	}
}
