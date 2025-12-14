package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.model.DoctorModel;
import com.example.doctorhouse.domain.port.in.doctor.GetAllActiveDoctorsUseCase;
import com.example.doctorhouse.domain.port.in.doctor.GetDoctorAvailabilityUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// En este servicio verificamos la disponibilidad del doctor
@Service
public class DoctorService implements GetDoctorAvailabilityUseCase, GetAllActiveDoctorsUseCase {

    private static final LocalTime DAY_START = LocalTime.of(8, 0);
    private static final LocalTime DAY_END = LocalTime.of(18, 0);

    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final DoctorRepositoryPort doctorRepositoryPort; // Inyectar DoctorRepositoryPort

    public DoctorService(
            AppointmentRepositoryPort appointmentRepositoryPort,
            DoctorRepositoryPort doctorRepositoryPort // Añadir al constructor
    ) {
        this.appointmentRepositoryPort = appointmentRepositoryPort;
        this.doctorRepositoryPort = doctorRepositoryPort; // Asignar
    }

    @Override
    public List<LocalTime> execute(Long doctorId, LocalDate date) {

        List<AppointmentModel> appointments =
                appointmentRepositoryPort.findByDoctorIdAndDate(doctorId, date);

        List<LocalTime> availableSlots = new ArrayList<>();

        LocalTime currentSlotStart = DAY_START;

        // Iterar por los posibles slots de inicio de cita
        while (!currentSlotStart.plusMinutes(AppointmentModel.DEFAULT_DURATION_MINUTES).isAfter(DAY_END)) {
            boolean isConflict = false;
            LocalTime currentSlotEnd = currentSlotStart.plusMinutes(AppointmentModel.DEFAULT_DURATION_MINUTES);

            // Verificar si el slot actual entra en conflicto con alguna cita existente
            for (AppointmentModel existingAppointment : appointments) {
                LocalTime existingStart = existingAppointment.getStartAt();
                // Asegurarse de que endAt tenga un valor, si no, calcularlo
                LocalTime existingEnd = existingAppointment.getEndAt().orElse(existingStart.plusMinutes(AppointmentModel.DEFAULT_DURATION_MINUTES));

                // Calcular el fin de la cita existente con el tiempo de buffer
                LocalTime existingEndWithBuffer = existingEnd.plusMinutes(AppointmentModel.BUFFER_TIME_MINUTES);

                // Comprobar solapamiento:
                // Un conflicto ocurre si el nuevo slot empieza antes de que termine la cita existente (con buffer)
                // Y el nuevo slot termina después de que empieza la cita existente
                if (currentSlotStart.isBefore(existingEndWithBuffer) && currentSlotEnd.isAfter(existingStart)) {
                    isConflict = true;
                    break; // Hay conflicto, no es necesario revisar más citas para este slot
                }
            }

            if (!isConflict) {
                availableSlots.add(currentSlotStart);
            }

            // Mover al siguiente slot
            currentSlotStart = currentSlotStart.plusMinutes(AppointmentModel.DEFAULT_DURATION_MINUTES);
        }

        return availableSlots;
    }

    @Override
    public List<DoctorModel> execute() {
        return doctorRepositoryPort.findAllActiveDoctors();
    }
}
