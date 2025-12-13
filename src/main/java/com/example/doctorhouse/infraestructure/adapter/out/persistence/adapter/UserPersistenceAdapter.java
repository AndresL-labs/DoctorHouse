package com.example.doctorhouse.infraestructure.adapter.out.persistence.adapter;

import com.example.doctorhouse.domain.model.UserModel;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.UserEntity;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.repository.UserJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserPersistenceAdapter implements UserRepositoryPort {

    private final UserJpaRepository jpaRepository;
    private final UserPersistenceMapper mapper;

    public UserPersistenceAdapter(UserJpaRepository jpaRepository, UserPersistenceMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public boolean existsByDni(String dni) {
        return jpaRepository.existsByDni(dni);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.existsByEmail(email);
    }

    @Override
    public UserModel save(UserModel userModel) {
        UserEntity entity = mapper.toEntity(userModel);
        UserEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<UserModel> findByEmail(String email) {
        return jpaRepository.findByEmail(email).map(mapper::toDomain);
    }
}
