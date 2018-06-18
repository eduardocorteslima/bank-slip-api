package com.contaazul.bankslip.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.GenericGenerator;

import com.contaazul.bankslip.enums.BankslipStatus;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Bankslip {

	@Id
	@GeneratedValue(generator = "uuid2")
	@GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
	@JsonProperty("id")
	private UUID id;

	@NotNull(groups = { New.class }, message = "The value of the due_date field can not be null.")
	@JsonProperty("due_date")
	@Column(nullable = false)
	private LocalDate dueDate;

	@NotNull(groups = { New.class }, message = "The value of the total_in_cents field can not be null.")
	@JsonProperty("total_in_cents")
	@Column(nullable = false)
	private BigDecimal totalInCents;

	@NotBlank(groups = { New.class }, message = "The value of the customer field can not be empty.")
	@JsonProperty("customer")
	@Column(nullable = false)
	private String customer;

	@NotNull(groups = { New.class, ChangeStatus.class }, message = "The value of the status field can not be null.")
	@JsonProperty("status")
	@Column(nullable = false)
	private BankslipStatus status;

	@JsonInclude(Include.NON_NULL)
	@Transient
	private BigDecimal fine;

	
	public interface New {
	}

	public interface ChangeStatus {
	}

}
