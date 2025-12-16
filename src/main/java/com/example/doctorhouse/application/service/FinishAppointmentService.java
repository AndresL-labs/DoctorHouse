package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.domain.model.MedicalRecord;
import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import com.example.doctorhouse.domain.port.in.FinishAppointmentUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.MedicalRecordRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FinishAppointmentService implements FinishAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final MedicalRecordRepositoryPort medicalRecordRepository;

    @Override
    @Transactional
    public void finishAppointment(Long appointmentId, MedicalRecord medicalRecord) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        appointment.setStatus(AppointmentStatus.FINALIZADA);
        appointmentRepository.save(appointment);

        // Check if there is already a medical record for this appointment logic
        medicalRecordRepository.findByAppointmentId(appointmentId).ifPresentOrElse(
                existingRecord -> {
                    medicalRecord.setId(existingRecord.getId());
                },
                () -> {
                    medicalRecord.setId(null);
                });

        medicalRecord.setAppointmentId(appointmentId);
        medicalRecordRepository.save(medicalRecord);
    }
}
