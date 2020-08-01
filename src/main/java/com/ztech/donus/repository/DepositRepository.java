package com.ztech.donus.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ztech.donus.model.Deposit;

@Repository
public interface DepositRepository extends MongoRepository<Deposit, String> {
	List<Deposit> findByAccountCpfOrderByDate(String cpf);
}