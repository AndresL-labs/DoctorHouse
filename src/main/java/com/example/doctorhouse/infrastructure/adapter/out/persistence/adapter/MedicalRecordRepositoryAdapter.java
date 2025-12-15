package com.example.doctorhouse.infrastructure.adapter.out.persistence.adapter;

import com.example.doctorhouse.domain.model.MedicalRecord;
import com.example.doctorhouse.domain.port.out.MedicalRecordRepositoryPort;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper.MedicalRecordPersistenceMapper;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.repository.MedicalRecordJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MedicalRecordRepositoryAdapter implements MedicalRecordRepositoryPort {

    private final MedicalRecordJpaRepository jpaRepository;
    private final MedicalRecordPersistenceMapper mapper;

    @Override
    public Optional<MedicalRecord> findByAppointmentId(Long appointmentId) {
        return jpaRepository.findByAppointment_Id(appointmentId)
                .map(mapper::toDomain);
    }
}
