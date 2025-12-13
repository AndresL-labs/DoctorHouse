package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;

import java.time.LocalDate;
import java.util.List;

public interface AppointmentRepositoryPort {

    List<AppointmentRouteModel> findTodayRouteByDoctor(
            Integer doctorId,
            LocalDate today
    );
}
