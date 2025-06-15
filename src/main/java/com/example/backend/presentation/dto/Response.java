package com.example.backend.presentation.dto;

public record Response<T>(
        T data,
        String message
) {
}
