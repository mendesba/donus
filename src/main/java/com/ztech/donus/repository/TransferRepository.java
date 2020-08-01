package com.ztech.donus.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ztech.donus.model.Transfer;

@Repository
public interface TransferRepository extends MongoRepository<Transfer, String> {
	List<Transfer> findByAccountCpfOrderByDate(String cpf);
}