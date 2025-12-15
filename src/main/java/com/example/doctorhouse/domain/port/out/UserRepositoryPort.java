package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.User;
import java.util.Optional;

public interface UserRepositoryPort {
    User save(User user);

    Optional<User> findById(Long id);

    Optional<User> findByEmail(String email);

    Optional<User> findByDni(String dni);
}
