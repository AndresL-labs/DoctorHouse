package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentEvolution;
import com.example.doctorhouse.domain.model.dto.command.RegisterAppointmentEvolutionCommand;
import com.example.doctorhouse.domain.port.out.AppointmentEvolutionRepositoryPort;
import com.example.doctorhouse.domain.port.out.AppointmentExistencePort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RegisterAppointmentEvolutionServiceTest {

    private AppointmentEvolutionRepositoryPort evolutionRepository;
    private AppointmentExistencePort appointmentExistencePort;
    private RegisterAppointmentEvolutionService service;

    @BeforeEach
    void setUp() {
        evolutionRepository = mock(AppointmentEvolutionRepositoryPort.class);
        appointmentExistencePort = mock(AppointmentExistencePort.class);
        service = new RegisterAppointmentEvolutionService(
                evolutionRepository,
                appointmentExistencePort
        );
    }

    @Test
    void shouldRegisterEvolutionWhenAppointmentExists() {
        // Arrange
        RegisterAppointmentEvolutionCommand command =
                new RegisterAppointmentEvolutionCommand(
                        1L,
                        "120/80",
                        75,
                        36.5,
                        98,
                        70.0,
                        "Hypertension",
                        "Patient stable"
                );

        when(appointmentExistencePort.existsAppointment(1L)).thenReturn(true);

        // Act
        service.registerEvolution(command);

        // Assert
        ArgumentCaptor<AppointmentEvolution> captor =
                ArgumentCaptor.forClass(AppointmentEvolution.class);

        verify(evolutionRepository, times(1)).save(captor.capture());

        AppointmentEvolution savedEvolution = captor.getValue();

        assertEquals(1L, savedEvolution.getAppointmentId());
        assertEquals("120/80", savedEvolution.getBloodPressure());
        assertEquals(75, savedEvolution.getHeartRate());
        assertEquals("Hypertension", savedEvolution.getDiagnosis());
        assertNotNull(savedEvolution.getCreatedAt());
    }

    @Test
    void shouldThrowExceptionWhenAppointmentDoesNotExist() {
        // Arrange
        RegisterAppointmentEvolutionCommand command =
                new RegisterAppointmentEvolutionCommand(
                        99L,
                        "120/80",
                        70,
                        36.8,
                        97,
                        68.0,
                        "Diagnosis",
                        "Observations"
                );

        when(appointmentExistencePort.existsAppointment(99L)).thenReturn(false);

        // Act & Assert
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.registerEvolution(command)
        );

        assertEquals("Appointment does not exist", exception.getMessage());

        verify(evolutionRepository, never()).save(any());
    }
}
