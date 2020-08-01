package com.ztech.donus.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.model.dto.AccountTransfer;

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
@EqualsAndHashCode(of = { "id" })
public class Transfer {
	@Id
	private String id;
	private AccountRequest account;
	private Date date;
	private Double amount;
	private String description;
	private AccountTransfer accountTransfer;
}