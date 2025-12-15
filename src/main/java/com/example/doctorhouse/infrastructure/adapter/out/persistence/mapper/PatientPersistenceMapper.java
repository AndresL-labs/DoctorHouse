package com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.PatientEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PatientPersistenceMapper {
    Patient toDomain(PatientEntity entity);

    @Mapping(target = "role", constant = "PACIENTE")
    @Mapping(target = "active", constant = "true")     // Aseguramos que esté activo
    @Mapping(target = "firstLogin", constant = "false") // O true, según tu lógica
    PatientEntity toEntity(Patient patient);
}
