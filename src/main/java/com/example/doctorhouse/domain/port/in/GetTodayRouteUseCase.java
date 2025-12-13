package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;

import java.util.List;

public interface GetTodayRouteUseCase {
    List<AppointmentRouteModel> execute(Integer doctorId);
}
