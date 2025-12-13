package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.UserModel;

public interface RegisterUserUseCase {
    void register(UserModel userModel);
}
