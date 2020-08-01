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

import com.google.gson.Gson;
import com.ztech.donus.model.Draft;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.service.DraftService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DraftControllerTest {
	private Date date;

	@Configuration
	static class ControllerTestConfiguration {
		@Bean
		public DraftService draftService() {
			return Mockito.mock(DraftService.class);
		}

		@Bean
		public DraftController draftController() {
			return new DraftController();
		}
	}

	@Autowired
	private DraftController controller;
	@Autowired
	private DraftService service;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws ParseException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		date = new Date();
	}

	@Test
	public void testFindAll() throws Exception {
		Mockito.when(this.service.findByAccountCpf(Mockito.anyString())).thenReturn(loadDrafts());
		mockMvc.perform(
				MockMvcRequestBuilders.get("/draft/{cpf}", "00000000000").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
	}

	@Test
	public void testSave() throws Exception {
		Mockito.when(this.service.save(Mockito.any())).thenReturn(loadDraft());
		String json = new Gson().toJson(loadAccount());
		mockMvc.perform(MockMvcRequestBuilders.post("/draft").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	private Draft loadDraft() {
		Draft draft = Draft.builder().id("1").account(loadAccount()).date(date).amount(100D)
				.description("Saque efetuado").build();
		return draft;
	}

	private AccountRequest loadAccount() {
		AccountRequest result = AccountRequest.builder().cpf("00000000000").name("Jose da Silva").build();
		return result;
	}

	private List<Draft> loadDrafts() {
		List<Draft> results = new ArrayList<Draft>();
		Draft d1 = Draft.builder().id("1").account(loadAccount()).date(date).amount(100D).description("Saque efetuado")
				.build();
		Draft d2 = Draft.builder().id("2").account(loadAccount()).date(date).amount(500D).description("Saque efetuado")
				.build();
		Draft d3 = Draft.builder().id("3").account(loadAccount()).date(date).amount(20D).description("Saque efetuado")
				.build();
		results.addAll(Arrays.asList(d1, d2, d3));
		return results;
	}
}