package com.ztech.donus.model.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountTransfer {
	@NotBlank(message = "[1]cpf.notblank")
	private String cpf;
	@NotBlank(message = "[2]cpf.notblank")
	private String name;
	@NotBlank(message = "[3]cpf.notblank")
	private String agency;
	@NotBlank(message = "[4]cpf.notblank")
	private String cc;
}