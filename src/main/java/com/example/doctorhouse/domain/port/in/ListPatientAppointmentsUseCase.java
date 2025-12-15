package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.AppointmentWithDoctor;

import java.util.List;
import java.util.Map;

public interface ListPatientAppointmentsUseCase {
    Map<String, List<AppointmentWithDoctor>> getAppointmentsGrouped(String patientEmail);
}
