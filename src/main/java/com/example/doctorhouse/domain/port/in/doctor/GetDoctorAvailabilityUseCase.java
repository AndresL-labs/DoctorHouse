package com.example.doctorhouse.domain.port.in.doctor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;


// Caso de uso: obtener disponibilidad diaria de un médico
public interface GetDoctorAvailabilityUseCase {

    List<LocalTime> execute(Long doctorId, LocalDate date);
}
