package com.example.doctorhouse.application.mapper;

import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto.AppointmentResponseDTO;

import java.time.LocalDateTime;

//Model -> DTO
public class AppointmentMapperDTO {

    private AppointmentMapperDTO() {
    }

    public static AppointmentResponseDTO toResponse(
            AppointmentModel model
    ) {
        AppointmentResponseDTO dto = new AppointmentResponseDTO();
        dto.setIdAppointment(model.getIdAppointment());
        dto.setPatientId(model.getPatientId());
        dto.setDoctorId(model.getDoctorId());
        dto.setAppointmentDateTime(LocalDateTime.of(model.getAppointmentDateTime(), model.getStartAt()));
        dto.setStartAt(model.getStartAt());
        dto.setEndAt(model.getEndAt().orElse(null));
        dto.setStatus(model.getStatus());
        return dto;
    }
}
