package com.example.backend.presentation.controller;

import com.example.backend.presentation.dto.UserRequest;
import com.example.backend.service.interfaces.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<Void> register(@RequestBody @Valid UserRequest request) {
        log.info("Registering user {}", request.username());
        userService.createAccount(request);
        log.info("Account created {}", request.username());
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAccount(@PathVariable String id) {
        log.info("Deleting user {}", id);
        userService.deleteAccount(id);
        log.info("Account deleted {}", id);
        return ResponseEntity.noContent().build();
    }
}
