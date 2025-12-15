package com.example.doctorhouse.infrastructure.adapter.out.persistence.adapter;

import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.UserEntity;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper.UserPersistenceMapper;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.repository.UserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final UserJpaRepository userJpaRepository;
    private final UserPersistenceMapper userPersistenceMapper;

    @Override
    public User save(User user) {
        UserEntity entity = userPersistenceMapper.toEntity(user);
        UserEntity saved = userJpaRepository.save(entity);
        return userPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userJpaRepository.findById(id).map(userPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(String email) {
        return userJpaRepository.findByEmail(email).map(userPersistenceMapper::toDomain);
    }

    @Override
    public Optional<User> findByDni(String dni) {
        return userJpaRepository.findByDni(dni).map(userPersistenceMapper::toDomain);
    }
}
