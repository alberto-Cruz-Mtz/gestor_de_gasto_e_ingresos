package com.example.backend.presentation.dto;

public record FundResponse(
    Long id,
    String name,
    String description,
    double amount,
    String createAt) {
}
