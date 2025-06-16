package com.example.backend.presentation.controller;

import com.example.backend.presentation.dto.FundRequest;
import com.example.backend.presentation.dto.FundResponse;
import com.example.backend.presentation.dto.Response;
import com.example.backend.service.interfaces.FundService;
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
@RequestMapping("/fund")
@RequiredArgsConstructor
public class FundController {

  private final FundService service;

  @PostMapping
  public ResponseEntity<Response<FundResponse>> createNewFund(@RequestBody @Valid FundRequest request,
      @RequestParam String userId) {
    FundResponse response = service.createFund(request, userId);

    return ResponseEntity.status(HttpStatus.CREATED).body(new Response<>(response, "fondo creado exitosamente"));
  }

  @PatchMapping("/{id}")
  public ResponseEntity<Void> updateFund(@RequestBody @Valid FundRequest request, @PathVariable Long id) {
    service.updateFund(request, id);
    return ResponseEntity.noContent().build();
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteFund(@PathVariable Long id) {
    service.deleteFund(id);
    return ResponseEntity.noContent().build();
  }

  @GetMapping
  public ResponseEntity<Response<List<FundResponse>>> findAll(@RequestParam String userId) {
    return ResponseEntity.ok(new Response<>(service.getFunds(userId), "fondos cargados"));
  }
}
