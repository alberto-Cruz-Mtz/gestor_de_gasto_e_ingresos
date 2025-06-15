package com.example.backend.presentation.dto;

import java.math.BigDecimal;

public record FundResponse(
        Long id,
        String name,
        String description,
        double amount,
        String createAt
) {
}
