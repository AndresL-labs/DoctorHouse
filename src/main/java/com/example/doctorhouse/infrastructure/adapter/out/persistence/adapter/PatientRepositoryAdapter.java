package com.example.doctorhouse.infrastructure.adapter.out.persistence.adapter;

import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.domain.port.out.PatientRepositoryPort;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.PatientEntity;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper.PatientPersistenceMapper;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.repository.PatientJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class PatientRepositoryAdapter implements PatientRepositoryPort {

    private final PatientJpaRepository patientJpaRepository;
    private final PatientPersistenceMapper patientPersistenceMapper;

    @Override
    public Patient save(Patient patient) {
        PatientEntity entity = patientPersistenceMapper.toEntity(patient);
        PatientEntity saved = patientJpaRepository.save(entity);
        return patientPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Patient> findById(Long id) {
        return patientJpaRepository.findById(id).map(patientPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Patient> findByEmail(String email) {
        return patientJpaRepository.findByEmail(email).map(patientPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Patient> findByDni(String dni) {
        return patientJpaRepository.findByDni(dni).map(patientPersistenceMapper::toDomain);
    }
}
