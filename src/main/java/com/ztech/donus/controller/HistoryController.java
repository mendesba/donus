package com.ztech.donus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztech.donus.model.dto.History;
import com.ztech.donus.service.HistoryService;

@RestController
@RequestMapping("/history")
public class HistoryController {
	@Autowired
	private HistoryService service;

	@GetMapping("/{cpf}")
	public ResponseEntity<List<History>> findByCpf(@PathVariable("cpf") String cpf) {
		List<History> results = service.findAllByCpf(cpf);
		return ResponseEntity.ok().body(results);
	}
}