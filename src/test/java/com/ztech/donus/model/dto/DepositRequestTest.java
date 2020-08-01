package com.ztech.donus.model.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class DepositRequestTest {
	@Test
	public void test() {
		DepositRequest result = load();
		assertNotNull("", result);
		assertEquals("", "Jose da Silva", result.getAccount().getName());
		assertEquals("", Double.valueOf(100), result.getAmount());
	}

	private DepositRequest load() {
		AccountRequest account = AccountRequest.builder().name("Jose da Silva").cpf("008.999.999.99").build();
		DepositRequest result = DepositRequest.builder().account(account).amount(100D).build();
		return result;
	}
}