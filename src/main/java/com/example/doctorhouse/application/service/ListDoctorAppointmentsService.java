package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentWithPatient;
import com.example.doctorhouse.domain.port.in.ListDoctorAppointmentsUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ListDoctorAppointmentsService implements ListDoctorAppointmentsUseCase {

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    @Override
    public List<AppointmentWithPatient> getTodaysAppointments(String doctorEmail) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        return appointmentRepositoryPort.findByDoctorEmailAndDateRange(doctorEmail, startOfDay, endOfDay);
    }
}
