package com.contaazul.bankslip.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import com.contaazul.bankslip.entity.Bankslip;

public interface BankslipRepository extends CrudRepository<Bankslip, UUID> {
}
