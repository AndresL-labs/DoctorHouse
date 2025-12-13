package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

/**
 * Puerto de salida que necesita HU-03.
 * Implementación JPA/Repository la debe entregar el responsable de persistencia (HU-02).
 */
public interface AppointmentRepositoryPort {

    /**
     * Devuelve las citas (con datos del paciente) para el doctor en la fecha dada.
     * La implementación es responsable de realizar el join con la entidad Usuario si lo requiere.
     *
     * @param doctorId id del médico
     * @param date fecha local (solo la porción date)
     * @return lista de AppointmentRouteModel (puede estar vacía)
     */
    List<AppointmentRouteModel> findTodayAppointmentsByDoctor(UUID doctorId, LocalDate date);
}
