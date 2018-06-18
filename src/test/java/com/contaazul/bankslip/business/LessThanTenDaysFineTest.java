package com.contaazul.bankslip.business;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

public class LessThanTenDaysFineTest {

	private Fine tax;

	@Test
	public void test_of_calculation_of_fine_rate_less_than_ten_days() {
		tax = new LessThanTenDaysFine(100000);
		assertThat(tax.calculate()).isEqualTo(500);
		
		tax = new LessThanTenDaysFine(10000);
		assertThat(tax.calculate()).isEqualTo(50);
		
		tax = new LessThanTenDaysFine(1000);
		assertThat(tax.calculate()).isEqualTo(5);
		
		tax = new LessThanTenDaysFine(100);
		assertThat(tax.calculate()).isEqualTo(0.5);
		
		tax = new LessThanTenDaysFine(10);
		assertThat(tax.calculate()).isEqualTo(0.05);
		
		tax = new LessThanTenDaysFine(223);
		assertThat(tax.calculate()).isEqualTo(1.115);
		
		tax = new LessThanTenDaysFine(0);
		assertThat(tax.calculate()).isEqualTo(0);
		
		
	}
}
