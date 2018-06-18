package com.contaazul.bankslip.business;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface FineCalculator {

	BigDecimal calculateFine(BigDecimal totalInCents, LocalDate dueDate);

}
