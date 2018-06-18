package com.contaazul.bankslip.service;

import java.util.List;
import java.util.Optional;

import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.enums.BankslipStatus;

public interface BankslipService {

	List<Bankslip> findAll();

	Optional<Bankslip> findById(String id);

	Bankslip createOne(Bankslip bankslip);

	void changeStatusBankslipById(String id, BankslipStatus status);

}
