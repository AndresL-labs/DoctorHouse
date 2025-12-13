package com.example.doctorhouse.infraestructure.adapter.in.mapper;

import com.example.doctorhouse.domain.model.UserModel;
import com.example.doctorhouse.infraestructure.adapter.in.dto.request.RegisterUserRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.OffsetDateTime;

@Mapper(
        componentModel = "spring",
        imports = OffsetDateTime.class
)
public interface UserRequestDtoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(
            target = "createdAt",
            expression = "java(OffsetDateTime.now())"
    )
    UserModel toDomain(RegisterUserRequestDto dto);
}
