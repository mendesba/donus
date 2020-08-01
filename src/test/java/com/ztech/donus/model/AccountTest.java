package com.ztech.donus.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;

public class AccountTest {
	private String id;

	@Before
	public void setUp() {
		id = new ObjectId().toHexString();
	}

	@Test
	public void test() {
		Account result = load();
		assertNotNull("", result);
		assertEquals("", id, result.getId());
		assertEquals("", "Jose da Silva", result.getName());
		assertEquals("", "008.999.999.99", result.getCpf());
		assertEquals("", "1234", result.getAgency());
		assertEquals("", "5678", result.getCc());
		assertEquals("", Double.valueOf(0), result.getBalance());
	}

	private Account load() {
		Account result = Account.builder().id(id).name("Jose da Silva").cpf("008.999.999.99").balance(0D).agency("1234")
				.cc("5678").build();
		return result;
	}
}