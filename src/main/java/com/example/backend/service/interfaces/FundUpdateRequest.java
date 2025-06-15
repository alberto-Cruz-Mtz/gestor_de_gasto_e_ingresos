package com.example.backend.service.interfaces;

import jakarta.validation.constraints.NotBlank;

public record FundUpdateRequest(
        @NotBlank String name,
        String description
) {
}
