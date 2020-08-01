package com.ztech.donus.service;

import java.util.List;

import com.ztech.donus.model.Draft;
import com.ztech.donus.model.dto.DraftRequest;

public interface DraftService {
	List<Draft> findByAccountCpf(String cpf);
	Draft save(DraftRequest deposit);
}