package com.example.doctorhouse.infrastructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByDni(String dni);
}
