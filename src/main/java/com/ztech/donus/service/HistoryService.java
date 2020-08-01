package com.ztech.donus.service;

import java.util.List;

import com.ztech.donus.model.dto.History;

public interface HistoryService {
	List<History> findAllByCpf(String cpfs);
}