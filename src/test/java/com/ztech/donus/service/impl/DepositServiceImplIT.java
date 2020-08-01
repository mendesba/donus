package com.ztech.donus.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.ztech.donus.DonusCodeChallengeApplication;
import com.ztech.donus.model.Account;
import com.ztech.donus.model.Deposit;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.model.dto.DepositRequest;
import com.ztech.donus.repository.AccountRepository;
import com.ztech.donus.repository.DepositRepository;
import com.ztech.donus.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DonusCodeChallengeApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class DepositServiceImplIT {
	@Autowired
	DepositServiceImpl service;
	@Autowired
	DepositRepository repository;
	@Autowired
	AccountRepository accountRepository;
	private List<Deposit> deposits = new ArrayList<>();

	@Before
	public void setUp() {
		accountRepository.deleteAll();
		Account account = Account.builder().id(new ObjectId().toHexString()).name("Jose da Silva").cpf("99701778006")
				.balance(2000D).agency("1234").cc("5678").build();
		accountRepository.save(account);
		repository.deleteAll();
		AccountRequest accountRequest = AccountRequest.builder().name("Jose da Silva").cpf("99701778006").build();
		Deposit deposit1 = Deposit.builder().account(accountRequest).id(new ObjectId().toHexString()).amount(100D)
				.description("Deposito efetuado").date(new Date()).build();
		Deposit deposit2 = Deposit.builder().account(accountRequest).id(new ObjectId().toHexString()).amount(100D)
				.description("Deposito efetuado").date(new Date()).build();
		deposits.add(deposit1);
		deposits.add(deposit2);
		repository.saveAll(deposits);
	}

	@Test
	public void testFindByCpfExceptionCpfInvalid() {
		try {
			service.findByAccountCpf("000.000.000-00");
			fail();
		} catch (Exception ex) {
			assertEquals("", Utils.getMessage("cpf.invalido"), ex.getMessage());
		}
	}

	@Test
	public void testFindByCpf() {
		List<Deposit> results = service.findByAccountCpf("99701778006");
		assertNotNull("", results);
		assertThat(results.size() > 0).isTrue();
	}

	@Test
	public void testSave() {
		AccountRequest accountRequest = AccountRequest.builder().cpf("99701778006").name("Jose da Silva").build();
		DepositRequest deposit = DepositRequest.builder().account(accountRequest).amount(100D).build();
		Deposit result = service.save(deposit);
		assertNotNull("", result);
		assertEquals("", "99701778006", result.getAccount().getCpf());
		assertEquals("", Double.valueOf(100), result.getAmount());
	}
}