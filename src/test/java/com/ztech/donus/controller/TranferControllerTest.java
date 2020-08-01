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
import com.ztech.donus.model.Transfer;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.model.dto.AccountTransfer;
import com.ztech.donus.service.TransferService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration
public class TransferControllerTest {
	private Date date;

	@Configuration
	static class ControllerTestConfiguration {
		@Bean
		public TransferService transferService() {
			return Mockito.mock(TransferService.class);
		}

		@Bean
		public TransferController transferController() {
			return new TransferController();
		}
	}

	@Autowired
	private TransferController controller;
	@Autowired
	private TransferService service;
	private MockMvc mockMvc;

	@Before
	public void setUp() throws ParseException {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(this.controller).build();
		date = new Date();
	}

	@Test
	public void testFindAll() throws Exception {
		Mockito.when(this.service.findByAccountCpf(Mockito.anyString())).thenReturn(loadTransfers());
		mockMvc.perform(
				MockMvcRequestBuilders.get("/transfer/{cpf}", "00000000000").contentType(MediaType.APPLICATION_JSON))
				.andDo(MockMvcResultHandlers.print()).andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(MockMvcResultMatchers.jsonPath("$").exists())
				.andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
	}

	@Test
	public void testSave() throws Exception {
		Mockito.when(this.service.save(Mockito.any())).thenReturn(loadTransfer());
		String json = new Gson().toJson(loadAccount());
		mockMvc.perform(MockMvcRequestBuilders.post("/transfer").content(json).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON)).andExpect(MockMvcResultMatchers.status().isCreated())
				.andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
	}

	private Transfer loadTransfer() {
		Transfer transfer = Transfer.builder().id("1").account(loadAccount()).date(date).amount(100D)
				.description("Deposito efetuado").build();
		return transfer;
	}

	private AccountRequest loadAccount() {
		AccountRequest result = AccountRequest.builder().cpf("00000000000").name("Jose da Silva").build();
		return result;
	}

	private AccountTransfer loadAccountTransfer() {
		AccountTransfer result = AccountTransfer.builder().cpf("11111111111").name("Maria da Silva").agency("12345")
				.cc("22222").build();
		return result;
	}

	private List<Transfer> loadTransfers() {
		List<Transfer> results = new ArrayList<Transfer>();
		Transfer t1 = Transfer.builder().id("1").account(loadAccount()).date(date).amount(100D)
				.description("Transferencia efetuada").accountTransfer(loadAccountTransfer()).build();
		Transfer t2 = Transfer.builder().id("2").account(loadAccount()).date(date).amount(500D)
				.description("Transferencia efetuada").accountTransfer(loadAccountTransfer()).build();
		results.addAll(Arrays.asList(t1, t2));
		return results;
	}
}