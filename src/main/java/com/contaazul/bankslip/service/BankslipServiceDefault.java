package com.contaazul.bankslip.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.contaazul.bankslip.business.FineCalculator;
import com.contaazul.bankslip.entity.Bankslip;
import com.contaazul.bankslip.enums.BankslipStatus;
import com.contaazul.bankslip.exceptions.BankslipNotFoundException;
import com.contaazul.bankslip.repository.BankslipRepository;

@Service
public class BankslipServiceDefault implements BankslipService {

	@Autowired
	private BankslipRepository bankslipRepository;

	@Autowired
	private FineCalculator taxCalculator;
	
	@Override
	public List<Bankslip> findAll() {
		return (List<Bankslip>) bankslipRepository.findAll();
	}

	@Override
	public Optional<Bankslip> findById(final String id) {

		UUID uuid = UUID.fromString(id);
		Optional<Bankslip> bankslip = bankslipRepository.findById(uuid);

		if (!bankslip.isPresent()) {
			throw new BankslipNotFoundException();
		}
		
		bankslip.get().setFine(taxCalculator.calculateFine(bankslip.get().getTotalInCents(), bankslip.get().getDueDate()));
		
		return bankslip;
	}

	@Override
	public Bankslip createOne(final Bankslip bankslip) {
		return bankslipRepository.save(bankslip);
	}

	@Override
	public void changeStatusBankslipById(final String id, final BankslipStatus status) {
		UUID uuid = UUID.fromString(id);

		Optional<Bankslip> bankslip = bankslipRepository.findById(uuid);

		if (bankslip.isPresent()) {
			bankslip.get().setStatus(status);
			bankslipRepository.save(bankslip.get());

		} else {
			throw new BankslipNotFoundException();
		}
	}

	
}
