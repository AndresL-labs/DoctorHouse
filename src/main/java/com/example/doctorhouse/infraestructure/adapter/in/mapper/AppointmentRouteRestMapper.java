package com.example.doctorhouse.infraestructure.adapter.in.mapper;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;
import com.example.doctorhouse.infraestructure.adapter.in.dto.AppointmentRouteResponseDTO;

public class AppointmentRouteRestMapper {

    public static AppointmentRouteResponseDTO toDto(AppointmentRouteModel model) {
        AppointmentRouteResponseDTO dto = new AppointmentRouteResponseDTO();
        dto.setAppointmentId(model.getAppointmentId());
        dto.setStartTime(model.getStartTime().toString());
        dto.setPatientName(model.getPatientFullName());
        dto.setPatientAddress(model.getPatientAddress());
        dto.setPatientCondition(model.getPatientCondition());
        dto.setStatus(model.getStatus());
        return dto;
    }
}
