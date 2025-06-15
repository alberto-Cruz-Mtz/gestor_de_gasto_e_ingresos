package com.example.backend.presentation.advice;

import com.example.backend.presentation.dto.ResponseError;
import com.example.backend.service.exception.NotFoundResourceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundResourceException.class)
    public ResponseEntity<ResponseError> handleNotFound(NotFoundResourceException ex) {
        log.error(ex.getMessage(), ex.getClass().toGenericString());
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ResponseError> handleIllegalArgument(IllegalArgumentException ex) {
        log.error(ex.getMessage(), ex.getClass().toGenericString());
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ResponseError> handleDataIntegrity(DataIntegrityViolationException ex) {
        log.error(ex.getMessage(), ex.getClass().toGenericString());
        return buildResponse(HttpStatus.CONFLICT, "Database constraint violation");
    }

    @ExceptionHandler(TransactionSystemException.class)
    public ResponseEntity<ResponseError> handleTransaction(TransactionSystemException ex) {
        log.error(ex.getMessage(), ex.getClass().toGenericString());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Transaction failed");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ResponseError> handleValidation(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining(", "));
        log.error(message, ex.getMessage());
        return buildResponse(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ResponseError> handleGeneral(Exception ex) {
        log.error(ex.getMessage(), ex.getClass().toGenericString());
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error occurred");
    }

    private ResponseEntity<ResponseError> buildResponse(HttpStatus status, String message) {
        ResponseError error = new ResponseError(status.value(), message, LocalDateTime.now());
        return new ResponseEntity<>(error, status);
    }
}
