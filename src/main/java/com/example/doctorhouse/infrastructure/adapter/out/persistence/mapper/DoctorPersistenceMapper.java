package com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.DoctorEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorPersistenceMapper {
    // Both Doctor domain and entity have 'role' inherited from User/UserEntity.
    // MapStruct handles it.
    // Conflicting 'active' field:
    // Doctor domain has 'active' (from User/Doctor combo)
    // DoctorEntity has 'doctorActive' which is meant to map to the 'doctors' table
    // field, and 'active' from 'UserEntity' parent.
    // The previous implementation of Doctor domain had 'active' as a single
    // boolean.
    // If I map Doctor.active -> DoctorEntity.doctorActive AND DoctorEntity.active

    @Mapping(target = "doctorActive", source = "active")
    DoctorEntity toEntity(Doctor doctor);

    @Mapping(target = "active", source = "doctorActive") // Map back doctorActive to domain active
    Doctor toDomain(DoctorEntity entity);
}
