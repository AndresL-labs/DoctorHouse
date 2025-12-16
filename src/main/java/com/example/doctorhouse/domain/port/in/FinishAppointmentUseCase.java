package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.MedicalRecord;

public interface FinishAppointmentUseCase {
    void finishAppointment(Long appointmentId, MedicalRecord medicalRecord);
}
