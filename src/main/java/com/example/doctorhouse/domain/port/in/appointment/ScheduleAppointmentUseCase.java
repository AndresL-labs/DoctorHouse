package com.example.doctorhouse.domain.port.in.appointment;

import com.example.doctorhouse.domain.model.AppointmentModel;

import java.time.LocalDate;
import java.time.LocalDateTime;

// Puerto de entrada, define QUÉ se puede hacer, no COMO.

public interface ScheduleAppointmentUseCase {

    AppointmentModel execute(
            Long patientId,
            Long doctorId,
            LocalDateTime startDateTime
    );
}
