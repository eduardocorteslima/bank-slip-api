package com.contaazul.bankslip.business;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class GreaterThanTenDaysFineTest {

	private Fine tax;

	@Test
	public void test_of_calculation_of_fine_rate_greater_than_ten_days() {
		tax = new GreaterThanTenDaysFine(100000);
		assertThat(tax.calculate()).isEqualTo(1000);
		
		tax = new GreaterThanTenDaysFine(10000);
		assertThat(tax.calculate()).isEqualTo(100);
		
		tax = new GreaterThanTenDaysFine(1000);
		assertThat(tax.calculate()).isEqualTo(10);
		
		tax = new GreaterThanTenDaysFine(100);
		assertThat(tax.calculate()).isEqualTo(1);
		
		tax = new GreaterThanTenDaysFine(10);
		assertThat(tax.calculate()).isEqualTo(0.1);
		
		tax = new GreaterThanTenDaysFine(223);
		assertThat(tax.calculate()).isEqualTo(2.23);
		
		tax = new GreaterThanTenDaysFine(0);
		assertThat(tax.calculate()).isEqualTo(0);
		
		
	}
}
