package com.example.doctorhouse.infraestructure.adapter.out.persistence.impl;

import com.example.doctorhouse.domain.model.AppointmentModel;

import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.AppointmentEntity;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper.AppointmentMapper;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.repository.AppointmentJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepositoryPort {

    private final AppointmentJpaRepository jpaRepository;

    public AppointmentRepositoryImpl(
            AppointmentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AppointmentModel save(AppointmentModel appointment) {
        AppointmentEntity entityToSave = AppointmentMapper.toEntity(appointment);
        AppointmentEntity savedEntity = jpaRepository.save(entityToSave);
        return AppointmentMapper.toModel(savedEntity);
    }

    @Override
    public List<AppointmentModel> findByDoctorIdAndDate(
            Long doctorId,
            LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(23, 59, 59);

        return jpaRepository
                .findByDoctorIdAndAppointmentDateTimeBetween(
                        doctorId,
                        startOfDay,
                        endOfDay)
                .stream()
                .map(AppointmentMapper::toModel)
                .collect(Collectors.toList());
    }

    @Override
    public java.util.Optional<AppointmentModel> findById(Long id) {
        return jpaRepository.findById(id)
                .map(AppointmentMapper::toModel);
    }
}
