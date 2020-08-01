package com.ztech.donus.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ztech.donus.model.Account;

@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
	Optional<Account> findByCpf(String cpf);
}