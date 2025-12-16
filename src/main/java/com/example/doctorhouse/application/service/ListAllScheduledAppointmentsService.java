package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.domain.port.in.ListAllScheduledAppointmentsUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListAllScheduledAppointmentsService implements ListAllScheduledAppointmentsUseCase {

    private final AppointmentRepositoryPort appointmentRepository;

    @Override
    public List<Appointment> execute() {
        return appointmentRepository.findAllScheduledOrderedByDate();
    }
}
