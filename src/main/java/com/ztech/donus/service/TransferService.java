package com.ztech.donus.service;

import java.util.List;

import com.ztech.donus.model.Transfer;
import com.ztech.donus.model.dto.TransferRequest;

public interface TransferService {
	List<Transfer> findByAccountCpf(String cpf);
	Transfer save(TransferRequest deposit);
}