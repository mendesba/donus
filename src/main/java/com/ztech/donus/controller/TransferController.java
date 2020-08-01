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

import com.ztech.donus.model.Transfer;
import com.ztech.donus.model.dto.TransferRequest;
import com.ztech.donus.service.TransferService;

@RestController
@RequestMapping("/transfer")
public class TransferController {
	@Autowired
	private TransferService service;

	@GetMapping("/{cpf}")
	public ResponseEntity<List<Transfer>> findByCpf(@PathVariable("cpf") String cpf) {
		List<Transfer> results = service.findByAccountCpf(cpf);
		return ResponseEntity.ok().body(results);
	}

	@PostMapping("")
	public ResponseEntity<Transfer> save(@RequestBody TransferRequest transferRequest) {
		return new ResponseEntity<Transfer>(service.save(transferRequest), HttpStatus.CREATED);
	}
}