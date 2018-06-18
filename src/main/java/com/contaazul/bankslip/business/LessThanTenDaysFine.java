package com.contaazul.bankslip.business;

public class LessThanTenDaysFine implements Fine {

	private static final double TAX = 0.005;
	private final double valor;

	public LessThanTenDaysFine(final double valor) {
		this.valor = valor;
	}

	@Override
	public double calculate() {
		return this.valor * TAX;
	}

}
