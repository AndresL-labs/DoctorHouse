package com.example.doctorhouse.domain.port.in.appointment;

import com.example.doctorhouse.domain.model.AppointmentModel;

public interface CreateAppointmentUseCase {
    AppointmentModel create(AppointmentModel appointment);
}
