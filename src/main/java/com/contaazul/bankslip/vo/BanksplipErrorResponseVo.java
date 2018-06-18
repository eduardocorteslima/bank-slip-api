package com.contaazul.bankslip.vo;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BanksplipErrorResponseVo {

	private int status;
	private List<String> messages;
	
}