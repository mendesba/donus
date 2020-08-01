package com.ztech.donus.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztech.donus.handler.exceptions.BusinessException;
import com.ztech.donus.model.Account;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.model.dto.AccountResponse;
import com.ztech.donus.repository.AccountRepository;
import com.ztech.donus.service.AccountService;
import com.ztech.donus.util.Utils;
import com.ztech.donus.util.ValidaCPF;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountRepository repository;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Account> findAll() {
		log.info("Initializing - Method findAll");
		Iterable<Account> results = repository.findAll();
		List<Account> accounts = new ArrayList<>();
		results.forEach(accounts::add);
		log.info("Finishing - Method findAll");
		return accounts;
	}

	@Override
	public Account findByCpf(String cpf) {
		log.info("Initializing - Method findByCpf");
		cpf = Utils.removeCaracter(cpf);
		if (!ValidaCPF.isCPF(cpf))
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		Optional<Account> result = repository.findByCpf(cpf);
		if (!result.isPresent())
			throw new BusinessException(Utils.getMessage("cpf.nao.encontrado"));
		log.info("Finishing - Method findByCpf");
		return result.get();
	}

	@Override
	public Account save(AccountRequest account) {
		log.info("Initializing - Method save");
		String cpf = account.getCpf();
		if (!ValidaCPF.isCPF(cpf))
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		Optional<Account> result = repository.findByCpf(cpf);
		if (!result.isPresent()) {
			Account accountSave = modelMapper.map(account, Account.class);
			accountSave.setAgency(Utils.getNumberAgency());
			accountSave.setCc(Utils.getNumberCc());
			return repository.save(accountSave);
		} else {
			throw new BusinessException(Utils.getMessage("cpf.cadastrado"));
		}
	}

	@Override
	public AccountResponse update(AccountRequest account) {
		Optional<Account> result = repository.findByCpf(account.getCpf()).map(item -> {
			item.setName(account.getName());
			return repository.save(item);
		});
		if (!result.isPresent())
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
		return accountResponse;
	}

	@Override
	public AccountResponse updateBalance(Account account) {
		if (!ValidaCPF.isCPF(account.getCpf()))
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		Optional<Account> result = repository.findByCpf(account.getCpf()).map(item -> {
			item.setBalance(account.getBalance());
			return repository.save(item);
		});
		if (!result.isPresent())
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		AccountResponse accountResponse = modelMapper.map(account, AccountResponse.class);
		return accountResponse;
	}
}