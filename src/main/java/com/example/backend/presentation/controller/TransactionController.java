package com.example.backend.presentation.controller;

import com.example.backend.presentation.dto.Response;
import com.example.backend.presentation.dto.TransactionRequest;
import com.example.backend.presentation.dto.TransactionResponse;
import com.example.backend.service.interfaces.TransactionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {

  private final TransactionService service;

  @PostMapping
  public ResponseEntity<TransactionResponse> createTransaction(@RequestBody @Valid TransactionRequest request,
      @RequestParam Long fundId) {
    TransactionResponse transactionResponse = service.createTransaction(request, fundId);
    return ResponseEntity.status(HttpStatus.CREATED).body(transactionResponse);
  }

  @PatchMapping("/{transactionId}")
  public ResponseEntity<TransactionResponse> updateTransaction(@RequestBody @Valid TransactionRequest request,
      @PathVariable Long transactionId) {
    TransactionResponse transactionResponse = service.updateTransaction(request, transactionId);
    return ResponseEntity.ok(transactionResponse);
  }

  @GetMapping
  public ResponseEntity<Response<List<TransactionResponse>>> getAllTransactions(@RequestParam Long fundId) {
    List<TransactionResponse> list = service.getTransactions(fundId);
    return ResponseEntity.ok(new Response<>(list, "fondos cargados"));
  }

  @DeleteMapping("/{transactionId}")
  public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
    service.deleteTransaction(transactionId);
    return ResponseEntity.noContent().build();
  }

  @GetMapping("/{transactionId}")
  public ResponseEntity<Response<TransactionResponse>> getTransaction(@PathVariable Long transactionId) {
    TransactionResponse response = service.getTransaction(transactionId);
    return ResponseEntity.ok(new Response<>(response, "Transaccion Encontrada"));
  }
}
