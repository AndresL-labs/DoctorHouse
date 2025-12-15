package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.User;

public interface RegisterUserUseCase {
    User registerUser(User user);
}
