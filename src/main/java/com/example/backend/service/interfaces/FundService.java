package com.example.backend.service.interfaces;

import com.example.backend.presentation.dto.FundRequest;
import com.example.backend.presentation.dto.FundResponse;

import java.util.List;

public interface FundService {

    FundResponse createFund(FundRequest request, String userId);

    FundResponse updateFund(FundRequest request, Long id);

    void deleteFund(Long id);

    List<FundResponse> getFunds(String userId);
}
