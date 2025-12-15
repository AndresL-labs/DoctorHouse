package com.example.doctorhouse.infrastructure.adapter.in.mapper;

import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.PatientRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.PatientResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdAt", ignore = true)
    Patient toDomain(PatientRequest request);

    PatientResponse toResponse(Patient patient);
}
