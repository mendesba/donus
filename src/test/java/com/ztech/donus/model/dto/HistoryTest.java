package com.ztech.donus.model.dto;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class HistoryTest {
	private Date date;

	@Before
	public void setUp() {
		date = new Date();
	}

	@Test
	public void test() {
		History result = load();
		assertNotNull("", result);
		assertEquals("", Double.valueOf(100), result.getAmount());
		assertEquals("", "Deposito efetuado", result.getDescription());
		assertEquals("", date, result.getDate());
	}

	private History load() {
		History result = History.builder().amount(100D).description("Deposito efetuado").date(date).build();
		return result;
	}
}