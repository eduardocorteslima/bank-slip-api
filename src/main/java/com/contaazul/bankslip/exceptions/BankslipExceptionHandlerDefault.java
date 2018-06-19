package com.contaazul.bankslip.exceptions;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.contaazul.bankslip.vo.BanksplipErrorResponseVo;

public class BankslipExceptionHandlerDefault {

	@ExceptionHandler
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public BanksplipErrorResponseVo unknownException(Exception ex) {
		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return new BanksplipErrorResponseVo(HttpStatus.INTERNAL_SERVER_ERROR.value(), errors);
	}

	protected BanksplipErrorResponseVo generateErrorResponse(HttpStatus httpStatus, Exception ex) {


		List<String> errors = new ArrayList<>();
		errors.add(ex.getMessage());
		return new BanksplipErrorResponseVo(httpStatus.value(), errors);
	}
}
