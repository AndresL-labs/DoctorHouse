package com.example.doctorhouse.infrastructure.adapter.out.persistence.adapter;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.DoctorEntity;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper.DoctorPersistenceMapper;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.repository.DoctorJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class DoctorRepositoryAdapter implements DoctorRepositoryPort {

    private final DoctorJpaRepository doctorJpaRepository;
    private final DoctorPersistenceMapper doctorPersistenceMapper;

    @Override
    public Doctor save(Doctor doctor) {
        DoctorEntity entity = doctorPersistenceMapper.toEntity(doctor);
        DoctorEntity saved = doctorJpaRepository.save(entity);
        return doctorPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Doctor> findById(Long id) {
        return doctorJpaRepository.findById(id).map(doctorPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Doctor> findByEmail(String email) {
        return doctorJpaRepository.findByEmail(email).map(doctorPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Doctor> findByLicenseNumber(String licenseNumber) {
        return doctorJpaRepository.findByLicenseNumber(licenseNumber)
                .map(doctorPersistenceMapper::toDomain);
    }

    @Override
    public java.util.List<Doctor> findAll() {
        return doctorJpaRepository.findAll().stream()
                .map(doctorPersistenceMapper::toDomain)
                .collect(java.util.stream.Collectors.toList());
    }
}
