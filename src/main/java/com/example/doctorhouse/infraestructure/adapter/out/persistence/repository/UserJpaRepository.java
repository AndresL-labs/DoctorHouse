package com.example.doctorhouse.infraestructure.adapter.out.persistence.repository;

import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserJpaRepository extends JpaRepository<UserEntity, Long> {
    boolean existsByDni(String dni);
    boolean existsByEmail(String email);
    Optional<UserEntity> findByEmail(String email);
}
