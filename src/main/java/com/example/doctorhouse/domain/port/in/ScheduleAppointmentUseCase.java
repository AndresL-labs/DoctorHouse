package com.example.doctorhouse.domain.port.in;

import java.time.LocalDateTime;

public interface ScheduleAppointmentUseCase {
    void scheduleAppointment(Long doctorId, String patientDni, LocalDateTime dateTime);
}
