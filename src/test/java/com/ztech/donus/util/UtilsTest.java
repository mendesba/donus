package com.ztech.donus.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

public class UtilsTest {
	Date dateCurrent;

	@Before
	public void setUp() {
		LocalDateTime localDateTime = LocalDateTime.now();
		dateCurrent = Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
	}

	@Test
	public void testGetMessage() {
		String msg = Utils.getMessage("common.test");
		assertNotNull("", msg);
		assertEquals("", "Teste automatizado.", msg);
	}

	@Test
	public void testGetOrderRegex() {
		String tag = Utils.getOrderRegex("[1]Test");
		assertNotNull("", tag);
		assertEquals("", "[1]", tag);
	}

	@Test
	public void testGetMessageValidation() {
		String msg = Utils.getMessageValidation("[1]common.test");
		assertNotNull("", msg);
		assertEquals("", "Teste automatizado.", msg);
	}
}