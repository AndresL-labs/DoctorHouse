package com.example.doctorhouse.infraestructure.adapter.out.persistence.impl;

import com.example.doctorhouse.domain.model.DoctorModel;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper.DoctorMapper;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.repository.DoctorJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class DoctorRepositoryImpl implements DoctorRepositoryPort {

    private final DoctorJpaRepository doctorJpaRepository;

    public DoctorRepositoryImpl(DoctorJpaRepository doctorJpaRepository) {
        this.doctorJpaRepository = doctorJpaRepository;
    }

    @Override
    public boolean isDoctorActive(Long doctorId) {
        return doctorJpaRepository.findById(doctorId)
                .map(DoctorMapper::toModel) // Primero mapear DoctorEntity a DoctorModel
                .map(DoctorModel::isActive) // Luego llamar isActive en el DoctorModel
                .orElse(false);
    }

    @Override
    public List<DoctorModel> findAllActiveDoctors() {
        return doctorJpaRepository.findByActiveTrue().stream()
                .map(DoctorMapper::toModel)
                .collect(Collectors.toList());
    }
}
