package com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.AppointmentEntity;

public class AppointmentMapper {

    private AppointmentMapper() {
    }

    public static AppointmentEntity toEntity(AppointmentModel model) {
        AppointmentEntity entity = new AppointmentEntity();

        entity.setId(model.getIdAppointment());
        entity.setPatientId(model.getPatientId());
        entity.setDoctorId(model.getDoctorId());
        entity.setAppointmentDateTime(model.getAppointmentDateTime());
        entity.setStartAt(model.getStartAt());
        entity.setEndAt(model.getEndAt().orElse(null));
        entity.setStatus(model.getStatus());
        entity.setCreatedAt(model.getCreatedAt());
        entity.setUpdatedAt(model.getUpdatedAt().orElse(null));

        return entity;
    }

    public static AppointmentModel toModel(AppointmentEntity entity) {
        AppointmentModel model = new AppointmentModel();

        model.setIdAppointment(entity.getId());
        model.setPatientId(entity.getPatientId());
        model.setDoctorId(entity.getDoctorId());
        model.setAppointmentDateTime(entity.getAppointmentDateTime());
        model.setStartAt(entity.getStartAt());
        model.setEndAt(entity.getEndAt());
        model.setStatus(entity.getStatus());
        model.setCreatedAt(entity.getCreatedAt());
        model.setUpdatedAt(entity.getUpdatedAt());

        return model;
    }
}
