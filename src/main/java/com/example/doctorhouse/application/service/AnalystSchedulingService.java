package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.domain.port.in.CheckAvailabilityUseCase;
import com.example.doctorhouse.domain.port.in.ListDoctorsUseCase;
import com.example.doctorhouse.domain.port.in.ScheduleAppointmentUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import com.example.doctorhouse.domain.port.out.PatientRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AnalystSchedulingService
        implements ListDoctorsUseCase, CheckAvailabilityUseCase, ScheduleAppointmentUseCase {

    private final DoctorRepositoryPort doctorRepositoryPort;
    private final AppointmentRepositoryPort appointmentRepositoryPort;
    private final PatientRepositoryPort patientRepositoryPort;

    @Override
    public List<Doctor> findAllDoctors() {
        return doctorRepositoryPort.findAll();
    }

    @Override
    public List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date) {
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Cannot schedule appointments for past dates.");
        }

        // Define working hours: 08:00 to 17:00
        List<LocalTime> allSlots = new ArrayList<>();
        for (int hour = 8; hour < 17; hour++) {
            allSlots.add(LocalTime.of(hour, 0));
        }

        // Filter for specific times if date is today
        if (date.equals(LocalDate.now())) {
            LocalTime now = LocalTime.now();
            allSlots.removeIf(slot -> slot.isBefore(now));
        }

        // Fetch existing appointments
        // Note: findByDoctorIdAndDate expects start/end of day presumably, or just
        // filtered by repository
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);
        List<Appointment> existingAppointments = appointmentRepositoryPort.findByDoctorIdAndDate(doctorId, startOfDay,
                endOfDay);

        // Filter out occupied slots
        // Assuming Appointment has a 'scheduledAt' which matches the slot start time
        List<LocalTime> bookedTimes = existingAppointments.stream()
                .map(Appointment::getScheduledAt)
                .map(LocalDateTime::toLocalTime)
                .collect(Collectors.toList());

        allSlots.removeAll(bookedTimes);

        return allSlots;
    }

    @Override
    @Transactional
    public void scheduleAppointment(Long doctorId, String patientDni, LocalDateTime dateTime) {
        if (dateTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Cannot schedule appointment in the past");
        }
        // Find Patient
        Patient patient = patientRepositoryPort.findByDni(patientDni)
                .orElseThrow(() -> new RuntimeException("Patient with DNI " + patientDni + " not found"));

        // Find Doctor
        Doctor doctor = doctorRepositoryPort.findById(doctorId)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));

        // Create Appointment
        Appointment appointment = new Appointment();
        appointment.setDoctorId(doctor.getId());
        appointment.setPatientId(patient.getId());
        appointment.setScheduledAt(dateTime);
        appointment.setDuration(60); // Default duration 60 mins
        appointment.setStatus(com.example.doctorhouse.domain.model.enums.AppointmentStatus.PROGRAMADA);
        appointment.setCreatedAt(LocalDateTime.now());

        appointmentRepositoryPort.save(appointment);
    }
}
