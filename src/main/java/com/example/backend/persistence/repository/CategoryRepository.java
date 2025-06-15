package com.example.backend.persistence.repository;

import com.example.backend.persistence.entity.CategoryEntity;
import com.example.backend.persistence.model.CategoryTransaction;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByCategoryTransaction(CategoryTransaction categoryTransaction);
}
