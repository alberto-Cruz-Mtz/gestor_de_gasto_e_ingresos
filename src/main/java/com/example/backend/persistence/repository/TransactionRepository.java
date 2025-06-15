package com.example.backend.persistence.repository;

import com.example.backend.persistence.entity.TransactionEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TransactionRepository extends CrudRepository<TransactionEntity, Long> {

    List<TransactionEntity> findByFundId(Long id);
}
