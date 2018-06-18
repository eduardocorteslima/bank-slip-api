package com.contaazul.bankslip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.contaazul.bankslip.repository.BankslipRepository;

@SpringBootApplication
public class BankslipApplication {

	@Autowired
	BankslipRepository bankslipRepository;

	public static void main(String[] args) {
		SpringApplication.run(BankslipApplication.class, args);
	}

}
