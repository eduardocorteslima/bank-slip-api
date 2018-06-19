package com.contaazul.bankslip.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.contaazul.bankslip.rest.BankslipGetRestController;
import com.contaazul.bankslip.vo.BanksplipErrorResponseVo;

@RestControllerAdvice(assignableTypes = { BankslipGetRestController.class })
public class BankslipGetExceptionHandler extends BankslipExceptionHandlerDefault {

	private static final String INVALID_ID_PROVIDED_IT_MUST_BE_A_VALID_UUID = "Invalid id provided - it must be a valid UUID";

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BanksplipErrorResponseVo constraintViolationException(IllegalArgumentException ex) {
		List<String> errors = new ArrayList<>();
		
		errors.add(INVALID_ID_PROVIDED_IT_MUST_BE_A_VALID_UUID);

		return new BanksplipErrorResponseVo(HttpStatus.BAD_REQUEST.value(), errors);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public BanksplipErrorResponseVo handleConstraintViolation(BankslipNotFoundException ex) {
		List<String> errors = new ArrayList<>();

		errors.add(ex.getMessage());

		return new BanksplipErrorResponseVo(HttpStatus.NOT_FOUND.value(), errors);
	}

}