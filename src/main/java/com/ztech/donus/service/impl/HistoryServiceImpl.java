package com.ztech.donus.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ztech.donus.handler.exceptions.BusinessException;
import com.ztech.donus.model.Deposit;
import com.ztech.donus.model.Draft;
import com.ztech.donus.model.Transfer;
import com.ztech.donus.model.dto.History;
import com.ztech.donus.service.DepositService;
import com.ztech.donus.service.DraftService;
import com.ztech.donus.service.HistoryService;
import com.ztech.donus.service.TransferService;
import com.ztech.donus.util.Utils;
import com.ztech.donus.util.ValidaCPF;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Service
public class HistoryServiceImpl implements HistoryService {
	@Autowired
	private DepositService depositService;
	@Autowired
	private TransferService transferService;
	@Autowired
	private DraftService draftService;
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public List<History> findAllByCpf(String cpf) {
		log.info("Initializing - Method findAllByCpf");
		cpf = Utils.removeCaracter(cpf);
		if (!ValidaCPF.isCPF(cpf))
			throw new BusinessException(Utils.getMessage("cpf.invalido"));
		List<History> histories = new ArrayList<History>();
		List<Deposit> deposits = depositService.findByAccountCpf(cpf);
		List<Transfer> tranfers = transferService.findByAccountCpf(cpf);
		List<Draft> drafts = draftService.findByAccountCpf(cpf);
		histories.addAll(
				deposits.stream().map(item -> modelMapper.map(item, History.class)).collect(Collectors.toList()));
		histories.addAll(
				tranfers.stream().map(item -> modelMapper.map(item, History.class)).collect(Collectors.toList()));
		histories
				.addAll(drafts.stream().map(item -> modelMapper.map(item, History.class)).collect(Collectors.toList()));
		histories = histories.stream().sorted(Comparator.comparing(History::getDate)).collect(Collectors.toList());
		log.info("Finishing - Method findAllByCpf");
		return histories;
	}
}