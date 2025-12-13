package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;

import java.util.List;
import java.util.UUID;

public interface GetTodayRouteUseCase {
    List<AppointmentRouteModel> getTodayRoute(UUID doctorId);
}
