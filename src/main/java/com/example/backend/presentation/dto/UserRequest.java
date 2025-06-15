package com.example.backend.presentation.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UserRequest(
        @Email @NotBlank String email,
        @NotBlank String username,
        @NotBlank @Size(min = 6, max = 16) String password,
        @NotEmpty List<FundRequest> funds
) {
}
