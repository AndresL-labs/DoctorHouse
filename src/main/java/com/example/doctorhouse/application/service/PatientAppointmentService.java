package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.domain.model.AppointmentWithDoctor;
import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import com.example.doctorhouse.domain.port.in.CancelAppointmentUseCase;
import com.example.doctorhouse.domain.port.in.ListPatientAppointmentsUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PatientAppointmentService implements ListPatientAppointmentsUseCase, CancelAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    public Map<String, List<AppointmentWithDoctor>> getAppointmentsGrouped(String patientEmail) {
        List<AppointmentWithDoctor> all = appointmentRepositoryPort.findByPatientEmail(patientEmail);

        Map<String, List<AppointmentWithDoctor>> grouped = new HashMap<>();
        grouped.put("PROGRAMADA", new ArrayList<>());
        grouped.put("HISTORIAL", new ArrayList<>()); // Combined Finalized and Cancelled

        for (AppointmentWithDoctor app : all) {
            if (app.getStatus() == AppointmentStatus.PROGRAMADA) {
                grouped.get("PROGRAMADA").add(app);
            } else {
                grouped.get("HISTORIAL").add(app);
            }
        }
        return grouped;
    }

    @Override
    public void cancelAppointment(Long appointmentId, String patientEmail) {
        Appointment appointment = appointmentRepositoryPort.findById(appointmentId)
                .orElseThrow(() -> new IllegalArgumentException("Cita no encontrada"));

        appointment.setStatus(AppointmentStatus.CANCELADA);
        appointmentRepositoryPort.save(appointment);
    }
}
