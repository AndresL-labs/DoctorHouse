package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.UserModel;

import java.util.Optional;

public interface LoginUserUseCase {
    String login(String email, String password);
    Optional<UserModel> getUserByEmail(String email);
}
