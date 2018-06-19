package com.contaazul.bankslip.exceptions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.contaazul.bankslip.rest.BankslipPostRestController;
import com.contaazul.bankslip.vo.BanksplipErrorResponseVo;

@RestControllerAdvice(assignableTypes = { BankslipPostRestController.class })
public class BankslipPostExceptionHandler {

	private static final String INVALID_BANKSLIP_PROVIDED_THE_POSSIBLE_REASONS_ARE = "Invalid bankslip provided.The possible reasons are:";
	private static final String BANKSLIP_NOT_PROVIDED_IN_THE_REQUEST_BODY = "Bankslip not provided in the request body";

	@ExceptionHandler
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public BanksplipErrorResponseVo constraintViolationException(HttpMessageNotReadableException ex) {

		List<String> errors = new ArrayList<>();
		errors.add(BANKSLIP_NOT_PROVIDED_IN_THE_REQUEST_BODY);

		return new BanksplipErrorResponseVo(HttpStatus.BAD_REQUEST.value(), errors);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	public BanksplipErrorResponseVo handleConstraintViolation(MethodArgumentNotValidException ex, WebRequest request) {

		List<String> errors = new ArrayList<>();
		errors.add(INVALID_BANKSLIP_PROVIDED_THE_POSSIBLE_REASONS_ARE);

		BindingResult bindingResult = ex.getBindingResult();
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		errors.addAll(fieldErrors.stream().map(FieldError::getField).collect(Collectors.toList()));

		return new BanksplipErrorResponseVo(HttpStatus.UNPROCESSABLE_ENTITY.value(), errors);
	}

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BanksplipErrorResponseVo unknownException(Exception ex) {

		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());

		return new BanksplipErrorResponseVo(HttpStatus.INTERNAL_SERVER_ERROR.value(), errors);

	}
}