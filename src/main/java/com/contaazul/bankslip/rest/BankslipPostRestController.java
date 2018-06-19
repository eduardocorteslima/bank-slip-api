package com.contaazul.bankslip.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.service.BankslipService;

@RestController
@RequestMapping("bankslips")
public class BankslipPostRestController {

	@Autowired
	private BankslipService bankslipService;

	@PostMapping
	public ResponseEntity<Bankslip> createOne(@Validated(Bankslip.New.class)  @RequestBody Bankslip bankslip) {

		return ResponseEntity.status(HttpStatus.CREATED).body(bankslipService.createOne(bankslip));

	}

}
