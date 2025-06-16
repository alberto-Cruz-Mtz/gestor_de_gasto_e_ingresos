package com.example.backend.service.implementation;

import com.example.backend.persistence.entity.FundEntity;
import com.example.backend.persistence.entity.UserEntity;
import com.example.backend.persistence.repository.FundRepository;
import com.example.backend.persistence.repository.UserRepository;
import com.example.backend.presentation.dto.FundRequest;
import com.example.backend.presentation.dto.FundResponse;
import com.example.backend.service.exception.NotFoundResourceException;
import com.example.backend.service.interfaces.FundService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FundServiceImpl implements FundService {

  private final FundRepository repository;
  private final UserRepository userRepository;

  @Transactional
  @Override
  public FundResponse createFund(FundRequest request, String userId) {
    UserEntity user = userRepository.findById(userId)
        .orElseThrow(() -> new NotFoundResourceException("User not found"));
    FundEntity fundEntity = new FundEntity(request.name(), request.amount(), request.description(), LocalDateTime.now(),
        user);

    if (repository.existsByNameAndUserId(request.name(), userId)) {
      throw new RuntimeException();
    }

    FundEntity savedEntity = repository.save(fundEntity);
    return this.toFundResponse(savedEntity);
  }

  @Transactional
  @Override
  public FundResponse updateFund(FundRequest request, Long id) {
    FundEntity fund = repository.findById(id).orElseThrow(() -> new NotFoundResourceException("Fund not found"));
    fund.setName(request.name());
    fund.setDescription(request.description());
    fund.setAmount(request.amount());

    FundEntity savedEntity = repository.save(fund);
    return this.toFundResponse(savedEntity);
  }

  @Transactional
  @Override
  public void deleteFund(Long id) {
    if (repository.existsById(id)) {
      repository.deleteById(id);
      return;
    }
    throw new NotFoundResourceException("Fund not found");
  }

  @Transactional(readOnly = true)
  @Override
  public List<FundResponse> getFunds(String userId) {
    return repository.findByUserId(userId).stream().map(this::toFundResponse).toList();
  }

  private FundResponse toFundResponse(FundEntity fundEntity) {
    return new FundResponse(
        fundEntity.getId(),
        fundEntity.getName(),
        fundEntity.getDescription(),
        fundEntity.getAmount(),
        fundEntity.getCreatedAt().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
  }
}
