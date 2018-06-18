package com.contaazul.bankslip.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.service.BankslipService;

@RestController
@RequestMapping("bankslips")
public class BankslipPutRestController {

	@Autowired
	private BankslipService bankslipService;

	@PutMapping("/{id}")
	public void updateStatusById(@PathVariable("id") String id, @Validated(Bankslip.ChangeStatus.class) @RequestBody Bankslip bankslip) {
		
		bankslipService.changeStatusBankslipById(id,bankslip.getStatus());

	}

}
