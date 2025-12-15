package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.model.enums.UserRole;
import com.example.doctorhouse.domain.port.in.RegisterUserUseCase;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;

public class RegisterUserService implements RegisterUserUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final PasswordEncoder passwordEncoder;

    public RegisterUserService(UserRepositoryPort userRepositoryPort,
                               PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User registerUser(User user) {

        if (user.getRole() != UserRole.ADMIN && user.getRole() != UserRole.ANALISTA) {
            throw new IllegalArgumentException("Aquí no se permite crear Médicos o Pacientes. Use la ruta específica.");
        }

        if (userRepositoryPort.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email ya existe.");
        }
        if (userRepositoryPort.findByDni(user.getDni()).isPresent()) {
            throw new IllegalArgumentException("DNI ya está registrado.");
        }
        user.setCreatedAt(LocalDateTime.now());

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);

        return userRepositoryPort.save(user);
    }
}
