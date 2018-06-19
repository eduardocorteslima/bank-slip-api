package com.contaazul.bankslip.business;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;

import org.springframework.stereotype.Component;

import com.contaazul.bankslip.exception.FineCalculatorException;

@Component
public class DefaultFineCalculator implements FineCalculator {

	private static final long MAX_DAYS = 10;

	private static final String EXCEPTION_MESSAGE_FINE_CALCULATE = "Error in calculating fine: %s";

	@Override
	public BigDecimal calculateFine(final BigDecimal totalInCents, final LocalDate dueDate) {
		try {

			Period delayedPeriod = Period.between(dueDate, LocalDate.now());

			if (delayedPeriod.getDays() == 0) {
				return BigDecimal.ZERO;
			}

			Fine fine;

			if (Math.abs(delayedPeriod.getDays()) > MAX_DAYS) {

				fine = new GreaterThanTenDaysFine(totalInCents.doubleValue());

			} else {

				fine = new LessThanTenDaysFine(totalInCents.doubleValue());
			}

			return BigDecimal.valueOf(fine.calculate());

		} catch (final Exception e) {

			throw new FineCalculatorException(String.format(EXCEPTION_MESSAGE_FINE_CALCULATE, e.getMessage()), e);

		}
	}
}
