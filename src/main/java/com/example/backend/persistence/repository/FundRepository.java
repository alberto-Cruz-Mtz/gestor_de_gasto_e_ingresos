package com.example.backend.persistence.repository;

import com.example.backend.persistence.entity.FundEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FundRepository extends CrudRepository<FundEntity, Long> {

    boolean existsByNameAndUserId(String name, String userId);
    List<FundEntity> findByUserId(String userId);
}
