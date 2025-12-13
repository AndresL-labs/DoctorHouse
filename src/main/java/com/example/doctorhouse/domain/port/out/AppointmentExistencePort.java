package com.example.doctorhouse.domain.port.out;


public interface AppointmentExistencePort {
    boolean existsAppointment(Long appointmentId);
}
