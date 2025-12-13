package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.exception.DuplicateUserException;
import com.example.doctorhouse.domain.model.Role;
import com.example.doctorhouse.domain.model.UserModel;
import com.example.doctorhouse.domain.port.in.RegisterUserUseCase;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(UserRepositoryPort userRepositoryPort, PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void register(UserModel userModel) {

        if (userRepositoryPort.existsByDni(userModel.getDni())) {
            throw new DuplicateUserException("The document already exists");
        }

        if (userRepositoryPort.existsByEmail(userModel.getEmail())) {
            throw new DuplicateUserException("The email already exists");
        }

        userModel.setPassword(
                passwordEncoder.encode(userModel.getPassword())
        );

        if (userModel.getRole() == null) {
            userModel.setRole(Role.PATIENT);
        }

        userModel.setActive(true);
        userModel.setCreatedAt(OffsetDateTime.now());

        userRepositoryPort.save(userModel);
    }
}
