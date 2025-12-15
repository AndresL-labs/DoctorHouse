package com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.MedicalRecord;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.MedicalRecordEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MedicalRecordPersistenceMapper {

    @Mapping(source = "appointment.id", target = "appointmentId")
    MedicalRecord toDomain(MedicalRecordEntity entity);

    @Mapping(source = "appointmentId", target = "appointment.id")
    MedicalRecordEntity toEntity(MedicalRecord domain);
}
