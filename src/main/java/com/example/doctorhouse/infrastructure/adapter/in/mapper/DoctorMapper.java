package com.example.doctorhouse.infrastructure.adapter.in.mapper;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.DoctorRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.DoctorResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface DoctorMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "role", constant = "MEDICO")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    Doctor toDomain(DoctorRequest request);

    DoctorResponse toResponse(Doctor doctor);
}
