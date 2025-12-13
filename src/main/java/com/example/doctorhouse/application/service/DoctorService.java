package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.port.in.doctor.GetDoctorAvailabilityUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

// En este servicio verificamos la disponibilidad del doctor
@Service
public class DoctorService implements GetDoctorAvailabilityUseCase {

    private static final LocalTime DAY_START = LocalTime.of(8, 0);
    private static final LocalTime DAY_END = LocalTime.of(18, 0);

    private final AppointmentRepositoryPort appointmentRepositoryPort;

    public DoctorService(
            AppointmentRepositoryPort appointmentRepositoryPort
    ) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
    }

    @Override
    public List<LocalTime> execute(Long doctorId, LocalDate date) {

        List<AppointmentModel> appointments =
                appointmentRepositoryPort.findByDoctorIdAndDate(doctorId, date);

        List<LocalTime> availableSlots = new ArrayList<>();

        LocalTime slot = DAY_START;

        while (!slot.plusMinutes(
                AppointmentModel.DEFAULT_DURATION_MINUTES
        ).isAfter(DAY_END)) {

            boolean occupied = false;

            for (AppointmentModel appointment : appointments) {
                if (appointment.getStartAt().equals(slot)) {
                    occupied = true;
                    break;
                }
            }

            if (!occupied) {
                availableSlots.add(slot);
            }

            slot = slot.plusMinutes(
                    AppointmentModel.DEFAULT_DURATION_MINUTES
            );
        }

        return availableSlots;
    }
}
