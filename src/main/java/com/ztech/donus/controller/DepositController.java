package com.ztech.donus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztech.donus.model.Deposit;
import com.ztech.donus.model.dto.DepositRequest;
import com.ztech.donus.service.DepositService;

@RestController
@RequestMapping("/deposit")
public class DepositController {
	@Autowired
	private DepositService service;

	@GetMapping("/{cpf}")
	public ResponseEntity<List<Deposit>> findByCpf(@PathVariable("cpf") String cpf) {
		List<Deposit> results = service.findByAccountCpf(cpf);
		return ResponseEntity.ok().body(results);
	}

	@PostMapping("")
	public ResponseEntity<Deposit> save(@RequestBody DepositRequest depositRequest) {
		return new ResponseEntity<Deposit>(service.save(depositRequest), HttpStatus.CREATED);
	}
}