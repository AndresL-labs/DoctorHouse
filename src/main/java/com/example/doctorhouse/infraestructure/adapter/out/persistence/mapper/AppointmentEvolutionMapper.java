package com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.AppointmentEvolution;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.AppointmentEvolutionEntity;

public class AppointmentEvolutionMapper {

    private AppointmentEvolutionMapper() {
    }

    public static AppointmentEvolutionEntity toEntity(AppointmentEvolution model) {
        AppointmentEvolutionEntity entity = new AppointmentEvolutionEntity();

        if (model.getId() != null) {
            entity.setId(model.getId());
        }
        entity.setAppointmentId(model.getAppointmentId());
        entity.setBloodPressure(model.getBloodPressure());
        entity.setHeartRate(model.getHeartRate());
        entity.setTemperature(model.getTemperature());
        entity.setOxygenSaturation(model.getOxygenSaturation());
        entity.setWeightKg(model.getWeightKg());
        entity.setDiagnosis(model.getDiagnosis());
        entity.setObservations(model.getObservations());
        entity.setCreatedAt(model.getCreatedAt());

        return entity;
    }

    public static AppointmentEvolution toModel(AppointmentEvolutionEntity entity) {
        return AppointmentEvolution.fromPersistence(
                entity.getId(),
                entity.getAppointmentId(),
                entity.getBloodPressure(),
                entity.getHeartRate(),
                entity.getTemperature(),
                entity.getOxygenSaturation(),
                entity.getWeightKg(),
                entity.getDiagnosis(),
                entity.getObservations(),
                entity.getCreatedAt()
        );
    }
}


