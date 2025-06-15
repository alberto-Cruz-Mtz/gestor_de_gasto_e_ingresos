package com.example.backend.presentation.dto;

import com.example.backend.persistence.model.CategoryTransaction;
import com.example.backend.persistence.model.TransactionType;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record TransactionRequest(
        @Digits(integer = 10, fraction = 2) @Positive double amount,
        @NotBlank String description,
        @NotNull CategoryTransaction category,
        @NotNull TransactionType transactionType
) {
}
