package com.ztech.donus.service;

import java.util.List;

import com.ztech.donus.model.Account;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.model.dto.AccountResponse;

public interface AccountService {
	List<Account> findAll();
	Account findByCpf(String cpf);
	Account save(AccountRequest account);
	AccountResponse update(AccountRequest account);
	AccountResponse updateBalance(Account account);
}