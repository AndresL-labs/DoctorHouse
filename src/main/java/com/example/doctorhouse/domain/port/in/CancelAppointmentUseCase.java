package com.example.doctorhouse.domain.port.in;

public interface CancelAppointmentUseCase {
    void cancelAppointment(Long appointmentId, String patientEmail);
}
