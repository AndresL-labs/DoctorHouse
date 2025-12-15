package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.exception.InvalidCredentialsException;
import com.example.doctorhouse.domain.model.UserModel;
import com.example.doctorhouse.domain.port.in.LoginUserUseCase;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import com.example.doctorhouse.infraestructure.config.security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LoginUserService implements LoginUserUseCase {

    private final AuthenticationManager authenticationManager;
    private final UserRepositoryPort userRepositoryPort;
    private final JwtUtils jwtUtils;

    public LoginUserService(AuthenticationManager authenticationManager, UserRepositoryPort userRepositoryPort, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepositoryPort = userRepositoryPort;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public String login(String email, String password) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // Credenciales válidas
        UserModel user = userRepositoryPort.findByEmail(email)
                .orElseThrow(InvalidCredentialsException::new);

        // Revisar si es el primer login
        boolean isFirstLogin = user.isFirstLogin();

        if (isFirstLogin) {
            user.setFirstLogin(false); // actualizar el flag
            userRepositoryPort.save(user); // persistir el cambio
        }

        // Generar token
        String token = jwtUtils.generateToken(
                user.getEmail(),
                user.getRole().name()
        );

        // Aquí podrías devolver un DTO que incluya token + firstLogin si quieres
        return token;
    }

    @Override
    public Optional<UserModel> getUserByEmail(String email) {
        return userRepositoryPort.findByEmail(email);
    }
}
