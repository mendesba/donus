package com.ztech.donus.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ztech.donus.model.Account;
import com.ztech.donus.model.dto.AccountRequest;
import com.ztech.donus.model.dto.AccountResponse;
import com.ztech.donus.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {
	@Autowired
	private AccountService service;

	@GetMapping("")
	public ResponseEntity<List<Account>> findAll() {
		List<Account> results = service.findAll();
		return ResponseEntity.ok().body(results);
	}

	@GetMapping("/{cpf}")
	public ResponseEntity<Account> findByCpf(@PathVariable("cpf") String cpf) {
		Account result = service.findByCpf(cpf);
		if (result != null) {
			return ResponseEntity.ok().body(result);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@PostMapping("")
	public ResponseEntity<Account> save(@RequestBody AccountRequest account) {
		return new ResponseEntity<Account>(service.save(account), HttpStatus.CREATED);
	}

	@PutMapping("")
	public ResponseEntity<AccountResponse> update(@RequestBody AccountRequest accountRequest) {
		AccountResponse accountResponse = service.update(accountRequest);
		if (accountResponse != null) {
			return ResponseEntity.ok().body(accountResponse);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}