package com.contaazul.bankslip.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.service.BankslipService;

@RestController
@RequestMapping("bankslips")
public class BankslipGetRestController {

	@Autowired
	private BankslipService bankslipService;

	@GetMapping
	public ResponseEntity<List<Bankslip>> findAll() {
		return ResponseEntity.ok(bankslipService.findAll());
	}

	@GetMapping("/{id}")
	public ResponseEntity<Optional<Bankslip>> findById(@PathVariable("id") String id) {
		return ResponseEntity.ok(bankslipService.findById(id));

	}

}
