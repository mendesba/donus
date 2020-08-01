package com.ztech.donus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztech.donus.handler.exceptions.BusinessException;
import com.ztech.donus.model.Account;
import com.ztech.donus.model.Draft;
import com.ztech.donus.model.dto.DraftRequest;
import com.ztech.donus.repository.DraftRepository;
import com.ztech.donus.service.AccountService;
import com.ztech.donus.service.DraftService;
import com.ztech.donus.util.Utils;
import com.ztech.donus.util.ValidaCPF;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Service
public class DraftServiceImpl implements DraftService {
	@Autowired
	private DraftRepository repository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Draft> findByAccountCpf(String cpf) {
		log.info("Initializing - Method findByAccountCpf");
		cpf = Utils.removeCaracter(cpf);
		if (!ValidaCPF.isCPF(cpf))
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		Iterable<Draft> results = repository.findByAccountCpfOrderByDate(cpf);
		List<Draft> deposits = new ArrayList<>();
		results.forEach(deposits::add);
		log.info("Finishing - Method findByAccountCpf");
		return deposits;
	}

	@Override
	public Draft save(DraftRequest draftRequest) {
		Account account = accountService.findByCpf(draftRequest.getAccount().getCpf());
		if (draftRequest.getAmount() <= 0)
			throw new BusinessException(Utils.getMessage("draft.valor.nao.pode.ser.menor.igual.zero"));
		Double tax = draftRequest.getAmount() * 0.01;
		Double balance = account.getBalance() - (draftRequest.getAmount() + tax);
		if (balance <= 0)
			throw new BusinessException(Utils.getMessage("saldo.insuficiente"));
		Draft draft = modelMapper.map(draftRequest, Draft.class);
		draft.setDate(new Date());
		draft.setDescription("Saque efetuado");
		repository.save(draft);
		Draft draftTax = Draft.builder().account(draftRequest.getAccount()).date(new Date()).description("Taxa cobrada")
				.amount(tax).build();
		repository.save(draftTax);
		account.setBalance(balance);
		accountService.updateBalance(account);
		return draft;
	}
}