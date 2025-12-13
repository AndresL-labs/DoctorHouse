package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;
import com.example.doctorhouse.domain.port.in.GetTodayRouteUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
public class GetTodayRouteService implements GetTodayRouteUseCase {

    private final AppointmentRepositoryPort appointmentRepository;

    public GetTodayRouteService(AppointmentRepositoryPort appointmentRepository) {
        this.appointmentRepository = appointmentRepository;
    }

    @Override
    public List<AppointmentRouteModel> execute(Integer doctorId) {

        // 📌 Siempre usamos la zona horaria de Colombia
        LocalDate today = LocalDate.now(ZoneId.of("America/Bogota"));

        // 📌 El orden ya viene desde la base de datos (JPQL ORDER BY)
        return appointmentRepository.findTodayRouteByDoctor(doctorId, today);
    }
}
