package com.example.backend.service.implementation;

import com.example.backend.persistence.entity.FundEntity;
import com.example.backend.persistence.entity.UserEntity;
import com.example.backend.persistence.repository.UserRepository;
import com.example.backend.presentation.dto.FundRequest;
import com.example.backend.presentation.dto.UserRequest;
import com.example.backend.service.exception.NotFoundResourceException;
import com.example.backend.service.interfaces.UserService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Transactional
    @Override
    public void createAccount(UserRequest request) {
        UserEntity user = new UserEntity(request.email(), request.password(), request.username());

        Set<FundEntity> funds = request.funds().stream()
                .map(fund -> this.toFundEntity(fund, user))
                .collect(Collectors.toSet());

        user.setFunds(funds);
        repository.save(user);
    }

    @Transactional
    @Override
    public void deleteAccount(String id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return;
        }
        throw new NotFoundResourceException("User not found");
    }

    private FundEntity toFundEntity(FundRequest request, UserEntity user) {
        return new FundEntity(
                request.name(),
                request.amount(),
                request.description(),
                LocalDateTime.now(), user);
    }
}
