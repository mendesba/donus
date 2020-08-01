package com.ztech.donus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztech.donus.handler.exceptions.BusinessException;
import com.ztech.donus.model.Account;
import com.ztech.donus.model.Deposit;
import com.ztech.donus.model.dto.DepositRequest;
import com.ztech.donus.repository.DepositRepository;
import com.ztech.donus.service.AccountService;
import com.ztech.donus.service.DepositService;
import com.ztech.donus.util.Utils;
import com.ztech.donus.util.ValidaCPF;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Service
public class DepositServiceImpl implements DepositService {
	@Autowired
	private DepositRepository repository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Deposit> findByAccountCpf(String cpf) {
		log.info("Initializing - Method findByAccountCpf");
		cpf = Utils.removeCaracter(cpf);
		if (!ValidaCPF.isCPF(cpf))
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		Iterable<Deposit> results = repository.findByAccountCpfOrderByDate(cpf);
		List<Deposit> deposits = new ArrayList<>();
		results.forEach(deposits::add);
		log.info("Finishing - Method findByAccountCpf");
		return deposits;
	}

	@Override
	public Deposit save(DepositRequest depositRequest) {
		Account account = accountService.findByCpf(depositRequest.getAccount().getCpf());
		if (depositRequest.getAmount() <= 0)
			throw new BusinessException(Utils.getMessage("deposit.valor.nao.pode.ser.menor.igual.zero"));
		Deposit deposit = modelMapper.map(depositRequest, Deposit.class);
		deposit.setDate(new Date());
		deposit.setDescription("Deposito efetuado");
		repository.save(deposit);
		Double bonus = depositRequest.getAmount() * 0.005;
		Deposit depositBonus = Deposit.builder().account(depositRequest.getAccount()).date(new Date())
				.description("Bônus efetuado").amount(bonus).build();
		repository.save(depositBonus);
		Double balance = account.getBalance() + depositRequest.getAmount() + depositBonus.getAmount();
		account.setBalance(balance);
		accountService.updateBalance(account);
		return deposit;
	}
}