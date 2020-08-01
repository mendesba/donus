package com.ztech.donus.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ztech.donus.model.dto.History;
import com.ztech.donus.service.HistoryService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class HistoryControllerTest {
	private Date date;

	@Configuration
	static class ControllerTestConfiguration {
		@Bean
		public HistoryService draftService() {
			return Mockito.mock(HistoryService.class);
		}

		@Bean
		public HistoryController historyController() {
			return new HistoryController();
		}
	}

	@Autowired
	private HistoryController controller;
	@Autowired
	private HistoryService service;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws ParseException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		date = new Date();
	}

	@Test
	public void testFindAll() throws Exception {
		Mockito.when(this.service.findAllByCpf(Mockito.anyString())).thenReturn(loadHistories());
		mockMvc.perform(
				MockMvcRequestBuilders.get("/history/{cpf}", "00000000000").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].amount").isNotEmpty());
	}

	private List<History> loadHistories() {
		List<History> results = new ArrayList<History>();
		History h1 = History.builder().date(date).amount(100D).description("Saque efetuado").build();
		History h2 = History.builder().date(date).amount(500D).description("Deposito efetuado").build();
		History h3 = History.builder().date(date).amount(20D).description("Transferencia efetuada").build();
		results.addAll(Arrays.asList(h1, h2, h3));
		return results;
	}
}