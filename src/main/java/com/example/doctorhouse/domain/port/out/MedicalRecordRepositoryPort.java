package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.MedicalRecord;

import java.util.Optional;

public interface MedicalRecordRepositoryPort {
    Optional<MedicalRecord> findByAppointmentId(Long appointmentId);
}
