package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.AppointmentModel;

import java.time.LocalDate;
import java.util.List;

// Puerto de salida, El dominio necesita datos, pero no sabe de dónde vienen

public interface AppointmentRepositoryPort {

    AppointmentModel save(AppointmentModel appointment);

    List<AppointmentModel> findByDoctorIdAndDate(
            Long doctorId,
            LocalDate date
    );
}
