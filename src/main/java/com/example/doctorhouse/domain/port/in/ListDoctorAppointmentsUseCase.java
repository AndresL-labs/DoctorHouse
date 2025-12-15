package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.AppointmentWithPatient;

import java.util.List;

public interface ListDoctorAppointmentsUseCase {
    List<AppointmentWithPatient> getTodaysAppointments(String doctorEmail);
}
