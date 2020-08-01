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
import com.ztech.donus.model.Transfer;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.model.dto.AccountTransfer;
import com.ztech.donus.model.dto.TransferRequest;
import com.ztech.donus.repository.AccountRepository;
import com.ztech.donus.repository.TransferRepository;
import com.ztech.donus.util.Utils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = DonusCodeChallengeApplication.class)
@TestPropertySource(locations = "classpath:test.properties")
public class TransferServiceImplIT {
	@Autowired
	TransferServiceImpl service;
	@Autowired
	TransferRepository repository;
	@Autowired
	AccountRepository accountRepository;
	private List<Transfer> transfers = new ArrayList<>();

	@Before
	public void setUp() {
		accountRepository.deleteAll();
		Account account = Account.builder().id(new ObjectId().toHexString()).name("Jose da Silva").cpf("99701778006")
				.balance(2000D).agency("1234").cc("5678").build();
		accountRepository.save(account);
		repository.deleteAll();
		AccountRequest accountRequest = AccountRequest.builder().name("Jose da Silva").cpf("99701778006").build();
		Transfer transfer1 = Transfer.builder().account(accountRequest).id(new ObjectId().toHexString()).amount(10D)
				.description("Saque efetuado").date(new Date()).build();
		Transfer transfer2 = Transfer.builder().account(accountRequest).id(new ObjectId().toHexString()).amount(50D)
				.description("Saque efetuado").date(new Date()).build();
		transfers.add(transfer1);
		transfers.add(transfer2);
		repository.saveAll(transfers);
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
		List<Transfer> results = service.findByAccountCpf("99701778006");
		assertNotNull("", results);
		assertThat(results.size() > 0).isTrue();
	}

	@Test
	public void testSave() {
		AccountRequest accountRequest = AccountRequest.builder().cpf("99701778006").name("Jose da Silva").build();
		TransferRequest deposit = TransferRequest.builder().account(accountRequest).amount(150D).accountTransfer(
				AccountTransfer.builder().agency("11111").cc("11111").name("Carlos Andrade").cpf("67325018010").build())
				.build();
		Transfer result = service.save(deposit);
		assertNotNull("", result);
		assertEquals("", "99701778006", result.getAccount().getCpf());
		assertEquals("", "67325018010", result.getAccountTransfer().getCpf());
		assertEquals("", Double.valueOf(150), result.getAmount());
	}
}