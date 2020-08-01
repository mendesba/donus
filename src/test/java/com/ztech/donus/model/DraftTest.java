package com.ztech.donus.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.ztech.donus.model.dto.AccountRequest;

public class DraftTest {
	private String id;
	private Date date;

	@Before
	public void setUp() {
		id = new ObjectId().toHexString();
		date = new Date();
	}

	@Test
	public void test() {
		Draft result = load();
		assertNotNull("", result);
		assertEquals("", "Jose da Silva", result.getAccount().getName());
		assertEquals("", id, result.getId());
		assertEquals("", Double.valueOf(50), result.getAmount());
		assertEquals("", "Saque efetuado", result.getDescription());
		assertEquals("", date, result.getDate());
	}

	private Draft load() {
		AccountRequest account = AccountRequest.builder().name("Jose da Silva").cpf("008.999.999.99").build();
		Draft result = Draft.builder().id(id).account(account).amount(50D).description("Saque efetuado").date(date)
				.build();
		return result;
	}
}