package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.MedicalRecord;

import java.util.Optional;

public interface GetMedicalRecordUseCase {
    Optional<MedicalRecord> getMedicalRecord(Long appointmentId);
}
