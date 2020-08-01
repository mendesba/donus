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

import com.ztech.donus.model.Draft;
import com.ztech.donus.model.dto.DraftRequest;
import com.ztech.donus.service.DraftService;

@RestController
@RequestMapping("/draft")
public class DraftController {
	@Autowired
	private DraftService service;

	@GetMapping("/{cpf}")
	public ResponseEntity<List<Draft>> findByCpf(@PathVariable("cpf") String cpf) {
		List<Draft> results = service.findByAccountCpf(cpf);
		return ResponseEntity.ok().body(results);
	}

	@PostMapping("")
	public ResponseEntity<Draft> save(@RequestBody DraftRequest draftRequest) {
		return new ResponseEntity<Draft>(service.save(draftRequest), HttpStatus.CREATED);
	}
}