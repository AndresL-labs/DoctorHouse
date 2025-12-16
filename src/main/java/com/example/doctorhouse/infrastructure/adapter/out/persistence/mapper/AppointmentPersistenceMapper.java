package com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentPersistenceMapper {

    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "doctor.id", target = "doctorId")
    Appointment toDomain(AppointmentEntity entity);

    @Mapping(target = "patient", ignore = true)
    @Mapping(target = "doctor", ignore = true)
    AppointmentEntity toEntity(Appointment domain);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.scheduledAt", target = "scheduledAt")
    @Mapping(source = "entity.duration", target = "duration")
    @Mapping(source = "entity.status", target = "status")
    @Mapping(source = "entity.patient.firstName", target = "patientName")
    @Mapping(source = "entity.patient.lastName", target = "patientLastName")
    @Mapping(source = "entity.patient.birthDate", target = "patientBirthDate")
    @Mapping(source = "entity.patient.allergies", target = "patientAllergies")
    @Mapping(source = "entity.patient.bloodType", target = "patientBloodType")
    @Mapping(source = "entity.patient.address", target = "patientAddress")
    @Mapping(source = "entity.patient.phone", target = "patientPhone")
    com.example.doctorhouse.domain.model.AppointmentWithPatient toAppointmentWithPatient(AppointmentEntity entity);

    @Mapping(source = "entity.id", target = "id")
    @Mapping(source = "entity.scheduledAt", target = "scheduledAt")
    @Mapping(source = "entity.duration", target = "duration")
    @Mapping(source = "entity.status", target = "status")
    @Mapping(source = "entity.doctor.firstName", target = "doctorName")
    @Mapping(source = "entity.doctor.lastName", target = "doctorLastName")
    @Mapping(source = "entity.doctor.specialty", target = "doctorSpecialty")
    @Mapping(source = "entity.doctor.licenseNumber", target = "doctorLicense")
    com.example.doctorhouse.domain.model.AppointmentWithDoctor toAppointmentWithDoctor(AppointmentEntity entity);
}