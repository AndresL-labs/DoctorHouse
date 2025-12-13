package com.example.doctorhouse.infraestructure.adapter.in.mapper;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;
import com.example.doctorhouse.infraestructure.adapter.in.dto.AppointmentRouteResponseDTO;

import java.time.format.DateTimeFormatter;

/**
 * Mapeo simple dominio -> DTO.
 */
public class AppointmentRouteRestMapper {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public static AppointmentRouteResponseDTO toDto(AppointmentRouteModel model) {
        if (model == null) return null;
        AppointmentRouteResponseDTO dto = new AppointmentRouteResponseDTO();
        dto.setAppointmentId(model.getAppointmentId());
        dto.setPatientName(model.getPatientName());
        dto.setPatientAddress(model.getPatientAddress());
        dto.setPatientCondition(model.getPatientCondition());
        dto.setStatus(model.getStatus());
        if (model.getStartAt() != null) {
            dto.setStartTime(model.getStartAt().toLocalTime().format(TIME_FORMATTER));
        } else {
            dto.setStartTime(null);
        }
        return dto;
    }
}
