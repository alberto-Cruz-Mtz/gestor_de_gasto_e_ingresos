package com.example.backend.persistence.repository;

import com.example.backend.persistence.entity.TransactionTypeEntity;
import com.example.backend.persistence.model.TransactionType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TransactionTypeRepository extends CrudRepository<TransactionTypeEntity, Long> {
    Optional<TransactionTypeEntity> findByTransactionType(TransactionType transactionType);
}
