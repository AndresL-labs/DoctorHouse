package com.example.doctorhouse.infrastructure.adapter.in.mapper;

import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.UserRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "firstLogin", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    User toDomain(UserRequest request);

    UserResponse toResponse(User user);
}
