package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.exception.InvalidAppointmentException;
import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import com.example.doctorhouse.domain.port.in.appointment.CreateAppointmentUseCase;
import com.example.doctorhouse.domain.port.in.appointment.ScheduleAppointmentUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
public class AppointmentService implements ScheduleAppointmentUseCase, CreateAppointmentUseCase {

    private final AppointmentRepositoryPort appointmentRepository;
    private final DoctorRepositoryPort doctorRepository;

    public AppointmentService(AppointmentRepositoryPort appointmentRepository,
                              DoctorRepositoryPort doctorRepository) {
        this.appointmentRepository = appointmentRepository;
        this.doctorRepository = doctorRepository;
    }

    @Override
    public AppointmentModel execute(
            Long patientId,
            Long doctorId,
            LocalDateTime startDateTime
    ) {
        if (!doctorRepository.isDoctorActive(doctorId)) {
            throw new InvalidAppointmentException("El médico seleccionado no está activo");
        }

        AppointmentModel appointment = new AppointmentModel();
        appointment.setPatientId(patientId);
        appointment.setDoctorId(doctorId);
        appointment.setAppointmentDateTime(startDateTime.toLocalDate());
        appointment.setStartAt(startDateTime.toLocalTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setCreatedAt(LocalDateTime.now());

        calculateEndTime(appointment);

        List<AppointmentModel> existingAppointments =
                appointmentRepository.findByDoctorIdAndDate(
                        doctorId,
                        startDateTime.toLocalDate()
                );

        for (AppointmentModel existing : existingAppointments) {
            if (hasConflictWithBuffer(appointment, existing)) {
                throw new InvalidAppointmentException(
                        "El médico no tiene disponibilidad en ese horario"
                );
            }
        }

        return appointmentRepository.save(appointment);
    }

    @Override
    public AppointmentModel create(AppointmentModel appointment) {
        calculateEndTime(appointment);

        LocalDate date = appointment.getAppointmentDateTime();
        List<AppointmentModel> existingAppointments =
                appointmentRepository.findByDoctorIdAndDate(
                        appointment.getDoctorId(),
                        date
                );

        for (AppointmentModel existing : existingAppointments) {
            if (hasConflictWithBuffer(appointment, existing)) {
                throw new InvalidAppointmentException(
                        "Appointment time conflict"
                );
            }
        }

        return appointmentRepository.save(appointment);
    }

    private void calculateEndTime(AppointmentModel appointment) {
        LocalTime startAt = appointment.getStartAt();
        LocalTime endAt = startAt.plusMinutes(AppointmentModel.DEFAULT_DURATION_MINUTES);
        appointment.setEndAt(endAt);
    }

    private boolean hasConflictWithBuffer(AppointmentModel newAppointment, AppointmentModel existingAppointment) {
        LocalTime newStart = newAppointment.getStartAt();
        LocalTime newEnd = newAppointment.getEndAt().orElse(newStart.plusMinutes(AppointmentModel.DEFAULT_DURATION_MINUTES));

        LocalTime existingStart = existingAppointment.getStartAt();
        LocalTime existingEnd = existingAppointment.getEndAt().orElse(existingStart.plusMinutes(AppointmentModel.DEFAULT_DURATION_MINUTES));

        // Añadir buffer a la cita existente
        LocalTime existingEndWithBuffer = existingEnd.plusMinutes(AppointmentModel.BUFFER_TIME_MINUTES);

        // Comprobar solapamiento
        return newStart.isBefore(existingEndWithBuffer) && newEnd.isAfter(existingStart);
    }
}
