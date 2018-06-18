package com.contaazul.bankslip.business;

public class GreaterThanTenDaysFine implements Fine {

	private static final double TAX = 0.01; 
    private final double valor;
    
	public GreaterThanTenDaysFine(final double valor) {
		this.valor = valor;
	}

	@Override
	public double calculate() {
		return this.valor * TAX;
	}

}
