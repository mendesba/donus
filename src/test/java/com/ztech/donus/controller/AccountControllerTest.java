package com.ztech.donus.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
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
import com.ztech.donus.model.Account;
import com.ztech.donus.model.dto.AccountResponse;
import com.ztech.donus.service.AccountService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class AccountControllerTest {
	@Configuration
	static class ControllerTestConfiguration {
		@Bean
		public AccountService accountService() {
			return Mockito.mock(AccountService.class);
		}

		@Bean
		public AccountController accountController() {
			return new AccountController();
		}
	}

	@Autowired
	private AccountController controller;
	@Autowired
	private AccountService service;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws ParseException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
	}

	@Test
	public void testFindAll() throws Exception {
		Mockito.when(this.service.findAll()).thenReturn(loadAccounts());
		mockMvc.perform(MockMvcRequestBuilders.get("/account").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
	}

	@Test
	public void testFindByAccount() throws Exception {
		Mockito.when(this.service.findByCpf(Mockito.anyString())).thenReturn(loadAccount());
		mockMvc.perform(
				MockMvcRequestBuilders.get("/account/{cpf}", "00000000000").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jose da Silva"));
	}

	@Test
	public void testFindByAccountNotFound() throws Exception {
		Mockito.when(this.service.findByCpf(Mockito.anyString())).thenReturn(null);
		mockMvc.perform(
				MockMvcRequestBuilders.get("/account/{cpf}", "11111111111").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isNotFound());
	}

	@Test
	public void testSave() throws Exception {
		Mockito.when(this.service.save(Mockito.any())).thenReturn(loadAccount());
		String json = new Gson().toJson(loadAccount());
		mockMvc.perform(MockMvcRequestBuilders.post("/account").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	@Test
	public void testUpdate() throws Exception {
		AccountResponse update = loadAccountResponse();
		update.setName("Jose da Silva Brasil");
		Mockito.when(service.update(Mockito.any())).thenReturn(update);
		String json = new Gson().toJson(update);
		mockMvc.perform(MockMvcRequestBuilders.put("/account").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$.cpf").value("00000000000"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Jose da Silva Brasil"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.balance").value(10D))
				.andExpect(MockMvcResultMatchers.jsonPath("$.agency").value("12345"))
				.andExpect(MockMvcResultMatchers.jsonPath("$.cc").value("11111"));
	}

	private Account loadAccount() {
		Account result = Account.builder().id("1").cpf("00000000000").name("Jose da Silva").balance(10D).agency("12345")
				.cc("11111").build();
		return result;
	}

	private AccountResponse loadAccountResponse() {
		AccountResponse result = AccountResponse.builder().cpf("00000000000").name("Jose da Silva").balance(10D)
				.agency("12345").cc("11111").build();
		return result;
	}

	private List<Account> loadAccounts() {
		List<Account> results = new ArrayList<Account>();
		Account a1 = Account.builder().id("1").cpf("00000000000").name("Jose da Silva").balance(10D).agency("12345")
				.cc("11111").build();
		Account a2 = Account.builder().id("2").cpf("11111111111").name("Maria da Silva").balance(20D).agency("12345")
				.cc("11112").build();
		Account a3 = Account.builder().id("3").cpf("22222222222").name("Antonio Souza").balance(30D).agency("12345")
				.cc("11113").build();
		results.addAll(Arrays.asList(a1, a2, a3));
		return results;
	}
}