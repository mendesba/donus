package com.ztech.donus.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztech.donus.handler.exceptions.BusinessException;
import com.ztech.donus.model.Account;
import com.ztech.donus.model.Transfer;
import com.ztech.donus.model.dto.TransferRequest;
import com.ztech.donus.repository.TransferRepository;
import com.ztech.donus.service.AccountService;
import com.ztech.donus.service.TransferService;
import com.ztech.donus.util.Utils;
import com.ztech.donus.util.ValidaCPF;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Service
public class TransferServiceImpl implements TransferService {
	@Autowired
	private TransferRepository repository;
	@Autowired
	private AccountService accountService;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<Transfer> findByAccountCpf(String cpf) {
		log.info("Initializing - Method findByAccountCpf");
		cpf = Utils.removeCaracter(cpf);
		if (!ValidaCPF.isCPF(cpf))
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		Iterable<Transfer> results = repository.findByAccountCpfOrderByDate(cpf);
		List<Transfer> transfers = new ArrayList<>();
		results.forEach(transfers::add);
		log.info("Finishing - Method findByAccountCpf");
		return transfers;
	}

	@Override
	public Transfer save(TransferRequest transferRequest) {
		Account account = accountService.findByCpf(transferRequest.getAccount().getCpf());
		if (transferRequest.getAmount() <= 0)
			throw new BusinessException(Utils.getMessage("transfer.valor.nao.pode.ser.menor.igual.zero"));
		if (!ValidaCPF.isCPF(transferRequest.getAccountTransfer().getCpf()))
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		Double balance = account.getBalance() - transferRequest.getAmount();
		if (balance <= 0)
			throw new BusinessException(Utils.getMessage("saldo.insuficiente"));
		Transfer transfer = modelMapper.map(transferRequest, Transfer.class);
		transfer.setDate(new Date());
		String description = String.format("Transferencia realizada para %s",
				transferRequest.getAccountTransfer().getName());
		transfer.setDescription(description);
		repository.save(transfer);
		account.setBalance(balance);
		accountService.updateBalance(account);
		return transfer;
	}
}