package com.example.backend.service.interfaces;

import com.example.backend.presentation.dto.UserRequest;

public interface UserService {

    void createAccount(UserRequest request);

    void deleteAccount(String id);
}
