package com.ztech.donus.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ztech.donus.DonusCodeChallengeApplication;
import com.ztech.donus.model.Account;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.model.dto.AccountResponse;
import com.ztech.donus.repository.AccountRepository;
import com.ztech.donus.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DonusCodeChallengeApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class AccountServiceImplIT {
	@Autowired
	AccountServiceImpl service;
	@Autowired
	AccountRepository repository;
	private List<Account> accounts = new ArrayList<>();
	@Autowired
	private ModelMapper modelMapper;

	@Before
	public void setUp() {
		repository.deleteAll();
		Account account1 = Account.builder().id(new ObjectId().toHexString()).name("Jose da Silva").cpf("83379548090")
				.balance(0D).agency("1234").cc("5678").build();
		Account account2 = Account.builder().id(new ObjectId().toHexString()).name("Maria Jose").cpf("67325018010")
				.balance(0D).agency("1234").cc("9999").build();
		accounts.add(account1);
		accounts.add(account2);
		repository.saveAll(accounts);
	}

	@Test
	public void testFindByCpfExceptionCpfInvalid() {
		try {
			service.findByCpf("000.000.000-00");
			fail();
		} catch (Exception ex) {
			assertEquals("", Utils.getMessage("cpf.invalido"), ex.getMessage());
		}
	}

	@Test
	public void testFindByCpfNotFound() {
		try {
			service.findByCpf("99701778006");
			fail();
		} catch (Exception ex) {
			assertEquals("", Utils.getMessage("cpf.nao.encontrado"), ex.getMessage());
		}
	}

	@Test
	public void testFindByCpf() {
		Account result = service.findByCpf("67325018010");
		assertNotNull("", result);
		assertEquals("", "Maria Jose", result.getName());
		assertEquals("", "67325018010", result.getCpf());
		assertEquals("", "1234", result.getAgency());
		assertEquals("", "9999", result.getCc());
		assertEquals("", Double.valueOf(0), result.getBalance());
	}

	@Test
	public void testFindAll() {
		List<Account> results = service.findAll();
		assertThat(results.size() > 0).isTrue();
		assertEquals("", accounts, results);
	}

	@Test
	public void testUpdate() {
		Account update = service.findByCpf("83379548090");
		AccountRequest request = modelMapper.map(update, AccountRequest.class);
		request.setName("Jose de Souza");
		AccountResponse result = service.update(request);
		Account resultUpdate = service.findByCpf(result.getCpf());
		assertNotNull("", resultUpdate);
		assertEquals("", "83379548090", result.getCpf());
		assertEquals("", "Jose de Souza", result.getName());
	}

	@Test
	public void testUpdateBalance() {
		Account result = service.findByCpf("83379548090");
		result.setBalance(100D);
		AccountResponse resultUpdate = service.updateBalance(result);
		assertNotNull("", resultUpdate);
		assertEquals("", "83379548090", result.getCpf());
		assertEquals("", "Jose da Silva", result.getName());
		assertEquals("", Double.valueOf(100), result.getBalance());
	}

	@Test
	public void testSave() {
		AccountRequest accountSave = AccountRequest.builder().cpf("15110425086").name("Guilherme Azevedo").build();
		Account result = service.save(accountSave);
		assertNotNull("", result.getId());
		assertEquals("", "Guilherme Azevedo", result.getName());
		assertEquals("", "15110425086", result.getCpf());
		assertEquals("", Double.valueOf(0), result.getBalance());
		assertNotNull("", result.getAgency());
		assertNotNull("", result.getCc());
	}
}