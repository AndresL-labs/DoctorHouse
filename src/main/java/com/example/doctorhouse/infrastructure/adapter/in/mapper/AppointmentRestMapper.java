package com.example.doctorhouse.infrastructure.adapter.in.mapper;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.AppointmentResponse;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.CreateAppointmentRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AppointmentRestMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "status", constant = "PROGRAMADA")
    Appointment toDomain(CreateAppointmentRequest request);

    AppointmentResponse toResponse(Appointment appointment);
}