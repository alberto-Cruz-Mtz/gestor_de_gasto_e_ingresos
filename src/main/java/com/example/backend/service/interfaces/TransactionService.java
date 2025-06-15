package com.example.backend.service.interfaces;

import com.example.backend.presentation.dto.TransactionRequest;
import com.example.backend.presentation.dto.TransactionResponse;

import java.util.List;

public interface TransactionService {

    TransactionResponse createTransaction(TransactionRequest request, Long fundId);

    TransactionResponse updateTransaction(TransactionRequest request, Long id);

    List<TransactionResponse> getTransactions(String id, Long fundId);

    void deleteTransaction(Long id);

    TransactionResponse getTransaction(Long id);
}
