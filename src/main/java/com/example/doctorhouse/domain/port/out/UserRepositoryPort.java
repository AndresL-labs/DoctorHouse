package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.UserModel;

import java.util.Optional;

public interface UserRepositoryPort {

    boolean existsByDni(String dni);
    boolean existsByEmail(String email);

    UserModel save(UserModel userModel);
    Optional<UserModel> findByEmail(String email);
}
