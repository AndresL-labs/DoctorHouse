package com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserPersistenceMapper {
    User toDomain(UserEntity entity);

    UserEntity toEntity(User user);
}
