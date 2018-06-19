package com.contaazul.bankslip.exception;

public class FineCalculatorException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public FineCalculatorException(String message, Exception e) {
		super(message, e);
	}

}
