package com.example.backend.persistence.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Entity
@Table(name = "transaction")
public class TransactionEntity {

    public TransactionEntity(double amount, String description, CategoryEntity category, TransactionTypeEntity transactionType, LocalDateTime createdAt) {
        this.amount = amount;
        this.description = description;
        this.category = category;
        this.transactionType = transactionType;
        this.transactionDate = createdAt;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private double amount;

    @Column(length = 20, nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDateTime transactionDate;

    @ManyToOne(targetEntity = CategoryEntity.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "category_id",  nullable = false)
    private CategoryEntity category;

    @ManyToOne(targetEntity = TransactionTypeEntity.class, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "transaction_type_id",  nullable = false)
    private TransactionTypeEntity transactionType;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = FundEntity.class)
    @JoinColumn(name = "fund_id",  nullable = false)
    private FundEntity fund;
}
