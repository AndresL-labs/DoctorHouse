package com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.UserModel;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.ERROR)
public interface UserPersistenceMapper {

    @Mapping(target = "firstLogin", source = "firstLogin")
    UserEntity toEntity(UserModel userModel);

    @Mapping(target = "firstLogin", source = "firstLogin")
    UserModel toDomain(UserEntity userEntity);

}
