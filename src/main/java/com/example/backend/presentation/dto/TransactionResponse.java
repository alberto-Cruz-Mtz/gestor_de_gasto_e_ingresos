package com.example.backend.presentation.dto;

import com.example.backend.persistence.model.CategoryTransaction;
import com.example.backend.persistence.model.TransactionType;

public record TransactionResponse(
        double amount,
        String description,
        CategoryTransaction category,
        TransactionType transactionType,
        String transactionDate
) {
}
