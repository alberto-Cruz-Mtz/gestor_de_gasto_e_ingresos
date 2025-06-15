package com.example.backend.presentation.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record FundRequest(
        @NotBlank String name,
        String description,
        @Digits(integer = 10, fraction = 2) @Positive double amount
) {
}
