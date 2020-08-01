package com.ztech.donus.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.ztech.donus.model.Draft;

@Repository
public interface DraftRepository extends MongoRepository<Draft, String> {
	List<Draft> findByAccountCpfOrderByDate(String cpf);
}