package com.contaazul.bankslip.exceptions;

public class BankslipNotFoundException extends RuntimeException {

	private static final String DEFAULT_MSG = "Bankslip not found with the specified id";
	private static final long serialVersionUID = 1L;

	public BankslipNotFoundException() {
		super(DEFAULT_MSG);
	}
}
