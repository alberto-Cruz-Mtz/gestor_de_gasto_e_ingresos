package com.example.backend.persistence.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "fund", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_id", "name"})
})
public class FundEntity {

    public FundEntity(String name, double amount, String description, LocalDateTime createdAt, UserEntity user) {
        this.name = name;
        this.amount = amount;
        this.description = description;
        this.createdAt = createdAt;
        this.user = user;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private double amount;

    @Column(length = 200)
    private String description;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY, targetEntity = UserEntity.class)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, mappedBy = "fund", orphanRemoval = true)
    private List<TransactionEntity> transactions;

    public void addAmount(double amount) {
        this.amount += amount;
    }

    public void removeAmount(double amount) {
        if(this.amount < amount) {
            throw new IllegalArgumentException("Amount must be greater than or equal to amount");
        }
        this.amount -= amount;
    }
}
