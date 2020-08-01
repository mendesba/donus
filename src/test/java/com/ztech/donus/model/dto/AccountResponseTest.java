package com.ztech.donus.model.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class AccountResponseTest {
	@Test
	public void test() {
		AccountResponse result = load();
		assertNotNull("", result);
		assertEquals("", "Jose da Silva", result.getName());
		assertEquals("", "008.999.999.99", result.getCpf());
		assertEquals("", "1234", result.getAgency());
		assertEquals("", "5678", result.getCc());
		assertEquals("", Double.valueOf(0), result.getBalance());
	}

	private AccountResponse load() {
		AccountResponse result = AccountResponse.builder().name("Jose da Silva").cpf("008.999.999.99").balance(0D)
				.agency("1234").cc("5678").build();
		return result;
	}
}