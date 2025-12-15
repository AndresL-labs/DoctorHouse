package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.Appointment;

public interface CreateAppointmentUseCase {
    Appointment create(Appointment appointment);
}
