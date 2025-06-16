package com.example.backend.service.implementation;

import com.example.backend.persistence.entity.CategoryEntity;
import com.example.backend.persistence.entity.FundEntity;
import com.example.backend.persistence.entity.TransactionEntity;
import com.example.backend.persistence.entity.TransactionTypeEntity;
import com.example.backend.persistence.model.CategoryTransaction;
import com.example.backend.persistence.model.TransactionType;
import com.example.backend.persistence.repository.CategoryRepository;
import com.example.backend.persistence.repository.FundRepository;
import com.example.backend.persistence.repository.TransactionRepository;
import com.example.backend.persistence.repository.TransactionTypeRepository;
import com.example.backend.presentation.dto.TransactionRequest;
import com.example.backend.presentation.dto.TransactionResponse;
import com.example.backend.service.exception.NotFoundResourceException;
import com.example.backend.service.interfaces.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {

  private final TransactionRepository repository;
  private final CategoryRepository categoryRepository;
  private final TransactionTypeRepository transactionTypeRepository;
  private final FundRepository fundRepository;

  @Transactional
  @Override
  public TransactionResponse createTransaction(TransactionRequest request, Long fundId) {
    FundEntity fund = fundRepository.findById(fundId)
        .orElseThrow(() -> new NotFoundResourceException("Fund not found"));
    updateFundOnCreate(fund, request.transactionType(), request.amount());

    TransactionEntity transaction = this.toTransactionEntity(request);
    transaction.setFund(fund);
    return this.toTransactionResponse(repository.save(transaction));
  }

  @Transactional
  @Override
  public TransactionResponse updateTransaction(TransactionRequest request, Long transactionId) {
    TransactionEntity transaction = this.getTransactionEntity(transactionId);
    updateFundOnUpdate(transaction.getFund(), transaction, request);

    transaction.setAmount(request.amount());
    transaction.setDescription(request.description());
    transaction.setCategory(this.getCategory(request.category()));
    transaction.setTransactionType(this.getTransactionType(request.transactionType()));

    return this.toTransactionResponse(repository.save(transaction));
  }

  @Transactional(readOnly = true)
  @Override
  public List<TransactionResponse> getTransactions(Long fundId) {
    return fundRepository
        .findById(fundId)
        .stream()
        .map(FundEntity::getTransactions)
        .flatMap(List::stream)
        .map(this::toTransactionResponse)
        .toList();
  }

  @Transactional
  @Override
  public void deleteTransaction(Long transactionId) {
    TransactionEntity transaction = this.getTransactionEntity(transactionId);
    updateFundOnDelete(transaction);
    repository.delete(transaction);
  }

  @Transactional(readOnly = true)
  @Override
  public TransactionResponse getTransaction(Long transactionId) {
    TransactionEntity transaction = this.getTransactionEntity(transactionId);
    return this.toTransactionResponse(transaction);
  }

  private TransactionEntity getTransactionEntity(Long transactionId) {
    return repository.findById(transactionId)
        .orElseThrow(() -> new NotFoundResourceException("Transaction not found"));
  }

  private TransactionResponse toTransactionResponse(TransactionEntity transaction) {
    return new TransactionResponse(
        transaction.getAmount(),
        transaction.getDescription(),
        transaction.getCategory().getCategoryTransaction(),
        transaction.getTransactionType().getTransactionType(),
        transaction.getTransactionDate().format(DateTimeFormatter.ISO_LOCAL_DATE));
  }

  private TransactionEntity toTransactionEntity(TransactionRequest request) {
    return new TransactionEntity(
        request.amount(),
        request.description(),
        this.getCategory(request.category()),
        this.getTransactionType(request.transactionType()),
        LocalDateTime.now());
  }

  private void updateFundOnCreate(FundEntity fund, TransactionType type, double amount) {
    updateFund(fund, type, amount);
  }

  private void updateFundOnUpdate(FundEntity fund, TransactionEntity transaction, TransactionRequest request) {
    this.restoreOldFund(transaction);
    this.updateFundOnCreate(fund, request.transactionType(), request.amount());
  }

  private void updateFundOnDelete(TransactionEntity transaction) {
    restoreOldFund(transaction);
  }

  private void updateFund(FundEntity fund, TransactionType type, double amount) {
    if (type == TransactionType.INCOME) {
      fund.addAmount(amount);
    } else
      fund.removeAmount(amount);
  }

  private void restoreOldFund(TransactionEntity transaction) {
    if (transaction.getTransactionType().getTransactionType() == TransactionType.INCOME) {
      transaction.getFund().removeAmount(transaction.getAmount());
    } else {
      transaction.getFund().addAmount(transaction.getAmount());
    }
  }

  private CategoryEntity getCategory(CategoryTransaction categoryTransaction) {
    return this.categoryRepository
        .findByCategoryTransaction(categoryTransaction)
        .orElse(new CategoryEntity(categoryTransaction));
  }

  private TransactionTypeEntity getTransactionType(TransactionType transactionType) {
    return this.transactionTypeRepository
        .findByTransactionType(transactionType)
        .orElse(new TransactionTypeEntity(transactionType));
  }
}
