package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.MedicalRecord;
import com.example.doctorhouse.domain.port.in.GetMedicalRecordUseCase;
import com.example.doctorhouse.domain.port.out.MedicalRecordRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GetMedicalRecordService implements GetMedicalRecordUseCase {

    private final MedicalRecordRepositoryPort medicalRecordRepositoryPort;

    @Override
    public Optional<MedicalRecord> getMedicalRecord(Long appointmentId) {
        return medicalRecordRepositoryPort.findByAppointmentId(appointmentId);
    }
}
