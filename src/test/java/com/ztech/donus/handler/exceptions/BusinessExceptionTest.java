package com.ztech.donus.handler.exceptions;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class BusinessExceptionTest {
	@Test
	public void testBusinessException() {
		BusinessException businessException = new BusinessException();
		BusinessException message = new BusinessException("erro", new NullPointerException());
		assertNotNull("", businessException);
		assertNotNull("", message);
	}
}