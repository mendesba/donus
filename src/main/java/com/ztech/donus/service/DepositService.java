package com.ztech.donus.service;

import java.util.List;

import com.ztech.donus.model.Deposit;
import com.ztech.donus.model.dto.DepositRequest;

public interface DepositService {
	List<Deposit> findByAccountCpf(String cpf);
	Deposit save(DepositRequest deposit);
}