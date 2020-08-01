package com.ztech.donus.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

import com.ztech.donus.model.dto.AccountRequest;

public class DepositTest {
	private String id;
	private Date date;

	@Before
	public void setUp() {
		id = new ObjectId().toHexString();
		date = new Date();
	}

	@Test
	public void test() {
		Deposit result = load();
		assertNotNull("", result);
		assertEquals("", id, result.getId());
		assertEquals("", "Jose da Silva", result.getAccount().getName());
		assertEquals("", Double.valueOf(100), result.getAmount());
		assertEquals("", "Deposito efetuado", result.getDescription());
		assertEquals("", date, result.getDate());
	}

	private Deposit load() {
		AccountRequest account = AccountRequest.builder().name("Jose da Silva").cpf("008.999.999.99").build();
		Deposit result = Deposit.builder().account(account).id(id).amount(100D).description("Deposito efetuado")
				.date(date).build();
		return result;
	}
}