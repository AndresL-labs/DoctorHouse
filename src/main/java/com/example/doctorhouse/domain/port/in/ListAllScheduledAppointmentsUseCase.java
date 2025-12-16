package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.Appointment;
import java.util.List;

public interface ListAllScheduledAppointmentsUseCase {
    List<Appointment> execute();
}
