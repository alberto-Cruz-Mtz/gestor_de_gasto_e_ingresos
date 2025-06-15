package com.example.backend.presentation.dto;

import java.time.LocalDateTime;

public record ResponseError(
        int status,
        String message,
        LocalDateTime timestamp
) {}
