package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.port.in.LoginUseCase;
import com.example.doctorhouse.domain.port.out.TokenProviderPort;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginService implements LoginUseCase {

    private final UserRepositoryPort userRepositoryPort;
    private final TokenProviderPort tokenProviderPort;
    private final PasswordEncoder passwordEncoder;

    public LoginService(UserRepositoryPort userRepositoryPort, TokenProviderPort tokenProviderPort,
            PasswordEncoder passwordEncoder) {
        this.userRepositoryPort = userRepositoryPort;
        this.tokenProviderPort = tokenProviderPort;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String login(String email, String password) {
        User user = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found or invalid credentials"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        return tokenProviderPort.generateToken(user);
    }
}
