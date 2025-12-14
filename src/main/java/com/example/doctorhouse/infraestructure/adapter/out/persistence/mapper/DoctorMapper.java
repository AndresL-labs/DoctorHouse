package com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper;

import com.example.doctorhouse.domain.model.DoctorModel;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.DoctorEntity;

public class DoctorMapper {

    private DoctorMapper() {
    }

    public static DoctorEntity toEntity(DoctorModel model) {
        DoctorEntity entity = new DoctorEntity();
        entity.setId(model.getId());
        entity.setSpecialty(model.getSpecialty());
        entity.setLicenseNumber(model.getLicenseNumber());
        entity.setActive(model.isActive());
        return entity;
    }

    public static DoctorModel toModel(DoctorEntity entity) {
        DoctorModel model = new DoctorModel();
        model.setId(entity.getId());
        model.setSpecialty(entity.getSpecialty());
        model.setLicenseNumber(entity.getLicenseNumber());
        model.setActive(entity.isActive());
        return model;
    }
}
