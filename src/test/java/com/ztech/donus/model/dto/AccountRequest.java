package com.ztech.donus.model.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AccountRequestTest {
	@Test
	public void test() {
		AccountRequest result = load();
		assertNotNull("", result);
		assertEquals("", "Jose da Silva", result.getName());
		assertEquals("", "008.999.999.99", result.getCpf());
	}

	private AccountRequest load() {
		AccountRequest result = AccountRequest.builder().name("Jose da Silva").cpf("008.999.999.99").build();
		return result;
	}
}