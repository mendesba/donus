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
import com.ztech.donus.model.Deposit;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.service.DepositService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class DepositControllerTest {
	private Date date;

	@Configuration
	static class ControllerTestConfiguration {
		@Bean
		public DepositService depositService() {
			return Mockito.mock(DepositService.class);
		}

		@Bean
		public DepositController depositController() {
			return new DepositController();
		}
	}

	@Autowired
	private DepositController controller;
	@Autowired
	private DepositService service;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws ParseException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		date = new Date();
	}

	@Test
	public void testFindAll() throws Exception {
		Mockito.when(this.service.findByAccountCpf(Mockito.anyString())).thenReturn(loadDeposits());
		mockMvc.perform(
				MockMvcRequestBuilders.get("/deposit/{cpf}", "00000000000").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
	}

	@Test
	public void testSave() throws Exception {
		Mockito.when(this.service.save(Mockito.any())).thenReturn(loadDeposit());
		String json = new Gson().toJson(loadAccount());
		mockMvc.perform(MockMvcRequestBuilders.post("/deposit").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	private Deposit loadDeposit() {
		Deposit deposit = Deposit.builder().id("1").account(loadAccount()).date(date).amount(100D)
				.description("Deposito efetuado").build();
		return deposit;
	}

	private AccountRequest loadAccount() {
		AccountRequest result = AccountRequest.builder().cpf("00000000000").name("Jose da Silva").build();
		return result;
	}

	private List<Deposit> loadDeposits() {
		List<Deposit> results = new ArrayList<Deposit>();
		Deposit d1 = Deposit.builder().id("1").account(loadAccount()).date(date).amount(100D)
				.description("Deposito efetuado").build();
		Deposit d2 = Deposit.builder().id("2").account(loadAccount()).date(date).amount(500D)
				.description("Deposito efetuado").build();
		Deposit d3 = Deposit.builder().id("3").account(loadAccount()).date(date).amount(20D)
				.description("Deposito efetuado").build();
		results.addAll(Arrays.asList(d1, d2, d3));
		return results;
	}
}