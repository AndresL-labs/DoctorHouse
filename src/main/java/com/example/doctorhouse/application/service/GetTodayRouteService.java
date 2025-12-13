package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;
import com.example.doctorhouse.domain.port.in.GetTodayRouteUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;

import java.time.Clock;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del caso de uso GetTodayRoute.
 * Usa ZoneId America/Bogota para definir "hoy" del médico.
 */
public class GetTodayRouteService implements GetTodayRouteUseCase {

    private static final ZoneId ZONE = ZoneId.of("America/Bogota");

    private final AppointmentRepositoryPort appointmentRepository;
    private final Clock clock;

    public GetTodayRouteService(AppointmentRepositoryPort appointmentRepository, Clock clock) {
        this.appointmentRepository = appointmentRepository;
        this.clock = clock;
    }

    public GetTodayRouteService(AppointmentRepositoryPort appointmentRepository) {
        this(appointmentRepository, Clock.system(ZONE));
    }

    @Override
    public List<AppointmentRouteModel> getTodayRoute(UUID doctorId) {
        if (doctorId == null) {
            throw new IllegalArgumentException("doctorId is required");
        }

        LocalDate today = LocalDate.now(clock.withZone(ZONE));
        List<AppointmentRouteModel> raw = appointmentRepository.findTodayAppointmentsByDoctor(doctorId, today);

        // Ordenar: por startAt ascendente, si empate por createdAt ascendente
        return raw.stream()
                .sorted(Comparator
                        .comparing(AppointmentRouteModel::getStartAt, Comparator.nullsLast(Comparator.naturalOrder()))
                        .thenComparing(AppointmentRouteModel::getCreatedAt, Comparator.nullsLast(Comparator.naturalOrder())))
                .collect(Collectors.toList());
    }
}
