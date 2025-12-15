package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.UserModel;

public interface RegisterUserUseCase {
    void register(UserModel userModel);
    void changePassword(String email, String oldPassword, String newPassword);
}
