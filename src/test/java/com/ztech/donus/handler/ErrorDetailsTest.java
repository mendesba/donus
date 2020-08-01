package com.ztech.donus.handler;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.Arrays;

import org.junit.Test;

public class ErrorDetailsTest {
	@Test
	public void Test() {
		ErrorDetails result = loadErrorDetails();
		assertNotNull("", result);
		assertEquals("", 1L, result.getTimestamp());
		assertEquals("", 404, result.getStatus());
		assertEquals("", "Erro", result.getTitle());
		assertThat(result.getDetails().size() > 0).isTrue();
	}

	private ErrorDetails loadErrorDetails() {
		return ErrorDetails.builder().timestamp(1L).status(404).title("Erro").details(Arrays.asList("Erro1", "Erro2"))
				.build();
	}
}