package com.example.doctorhouse.infraestructure.adapter.out.persistence.impl;

import com.example.doctorhouse.domain.model.AppointmentEvolution;
import com.example.doctorhouse.domain.port.out.AppointmentEvolutionRepositoryPort;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.AppointmentEvolutionEntity;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper.AppointmentEvolutionMapper;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.repository.AppointmentEvolutionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class AppointmentEvolutionRepositoryImpl implements AppointmentEvolutionRepositoryPort {

    private final AppointmentEvolutionJpaRepository jpaRepository;

    public AppointmentEvolutionRepositoryImpl(AppointmentEvolutionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AppointmentEvolution save(AppointmentEvolution evolution) {
        AppointmentEvolutionEntity entityToSave = AppointmentEvolutionMapper.toEntity(evolution);
        AppointmentEvolutionEntity savedEntity = jpaRepository.save(entityToSave);
        return AppointmentEvolutionMapper.toModel(savedEntity);
    }

    @Override
    public Optional<AppointmentEvolution> findByAppointmentId(Long appointmentId) {
        return jpaRepository.findByAppointmentId(appointmentId)
                .map(AppointmentEvolutionMapper::toModel);
    }
}

