package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentEvolution;
import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.model.dto.command.RegisterAppointmentEvolutionCommand;
import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import com.example.doctorhouse.domain.port.out.AppointmentEvolutionRepositoryPort;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.SecurityPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterAppointmentEvolutionServiceTest {

    private AppointmentEvolutionRepositoryPort evolutionRepository;
    private AppointmentRepositoryPort appointmentRepository;
    private SecurityPort securityPort;
    private RegisterAppointmentEvolutionService service;

    @BeforeEach
    void setUp() {
        evolutionRepository = mock(AppointmentEvolutionRepositoryPort.class);
        appointmentRepository = mock(AppointmentRepositoryPort.class);
        securityPort = mock(SecurityPort.class);
        service = new RegisterAppointmentEvolutionService(
                evolutionRepository,
                appointmentRepository,
                securityPort
        );
    }

    @Test
    void shouldRegisterEvolutionWhenAppointmentExists() {
        // Arrange
        Long appointmentId = 1L;
        Long doctorId = 1L;
        RegisterAppointmentEvolutionCommand command =
                new RegisterAppointmentEvolutionCommand(
                        appointmentId,
                        "120/80",
                        75,
                        36.5,
                        98,
                        70.0,
                        "Hypertension",
                        "Patient stable"
                );

        AppointmentModel appointment = new AppointmentModel();
        appointment.setIdAppointment(appointmentId);
        appointment.setDoctorId(doctorId);
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setStartAt(LocalTime.of(10, 0));
        appointment.setAppointmentDateTime(LocalDateTime.now());
        appointment.setCreatedAt(LocalDateTime.now());

        when(securityPort.getAuthenticatedUserId()).thenReturn(Optional.of(doctorId));
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.of(appointment));
        when(evolutionRepository.findByAppointmentId(appointmentId)).thenReturn(Optional.empty());
        when(evolutionRepository.save(any(AppointmentEvolution.class))).thenAnswer(invocation -> {
            AppointmentEvolution evolution = invocation.getArgument(0);
            return AppointmentEvolution.fromPersistence(
                    1L,
                    evolution.getAppointmentId(),
                    evolution.getBloodPressure(),
                    evolution.getHeartRate(),
                    evolution.getTemperature(),
                    evolution.getOxygenSaturation(),
                    evolution.getWeightKg(),
                    evolution.getDiagnosis(),
                    evolution.getObservations(),
                    evolution.getCreatedAt()
            );
        });
        when(appointmentRepository.save(any(AppointmentModel.class))).thenReturn(appointment);

        // Act
        AppointmentEvolution result = service.registerEvolution(command);

        // Assert
        ArgumentCaptor<AppointmentEvolution> evolutionCaptor =
                ArgumentCaptor.forClass(AppointmentEvolution.class);
        ArgumentCaptor<AppointmentModel> appointmentCaptor =
                ArgumentCaptor.forClass(AppointmentModel.class);

        verify(evolutionRepository, times(1)).save(evolutionCaptor.capture());
        verify(appointmentRepository, times(1)).save(appointmentCaptor.capture());

        AppointmentEvolution savedEvolution = evolutionCaptor.getValue();
        AppointmentModel savedAppointment = appointmentCaptor.getValue();

        assertEquals(appointmentId, savedEvolution.getAppointmentId());
        assertEquals("120/80", savedEvolution.getBloodPressure());
        assertEquals(75, savedEvolution.getHeartRate());
        assertEquals(36.5, savedEvolution.getTemperature());
        assertEquals(98, savedEvolution.getOxygenSaturation());
        assertEquals("Hypertension", savedEvolution.getDiagnosis());
        assertNotNull(savedEvolution.getCreatedAt());

        assertEquals(AppointmentStatus.COMPLETED, savedAppointment.getStatus());
        assertNotNull(savedAppointment.getEndAt().orElse(null));
        assertNotNull(savedAppointment.getUpdatedAt().orElse(null));
    }

    @Test
    void shouldThrowExceptionWhenAppointmentDoesNotExist() {
        // Arrange
        Long appointmentId = 99L;
        Long doctorId = 1L;
        RegisterAppointmentEvolutionCommand command =
                new RegisterAppointmentEvolutionCommand(
                        appointmentId,
                        "120/80",
                        70,
                        36.8,
                        97,
                        68.0,
                        "Diagnosis",
                        "Observations"
                );

        when(securityPort.getAuthenticatedUserId()).thenReturn(Optional.of(doctorId));
        when(appointmentRepository.findById(appointmentId)).thenReturn(Optional.empty());

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.registerEvolution(command)
        );

        assertEquals("Appointment not found", exception.getMessage());

        verify(evolutionRepository, never()).save(any());
        verify(appointmentRepository, never()).save(any());
    }
}
