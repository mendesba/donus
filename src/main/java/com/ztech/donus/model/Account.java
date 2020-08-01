package com.ztech.donus.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Document
@EqualsAndHashCode(of = { "cpf" })
public class Account {
	@Id
	private String id;
	private String cpf;
	private String name;
	@Builder.Default
	private Double balance = 0D;
	private String agency;
	private String cc;
}