package com.example.doctorhouse.application;


import com.example.doctorhouse.application.service.AppointmentService;
import com.example.doctorhouse.domain.exception.InvalidAppointmentException;
import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AppointmentServiceTest {

    // Mocks para los puertos de salida (dependencias del servicio)
    private final AppointmentRepositoryPort appointmentRepository = mock(AppointmentRepositoryPort.class);
    private final DoctorRepositoryPort doctorRepository = mock(DoctorRepositoryPort.class);

    // Instancia del servicio bajo prueba con sus dependencias mockeadas
    private final AppointmentService service = new AppointmentService(appointmentRepository, doctorRepository);

    @Test
    void shouldThrowExceptionWhenTimeConflictExists() {
        // Arrange (Organizar)

        // 1. Cita existente que simularemos que ya está en la base de datos
        AppointmentModel existingAppointment = new AppointmentModel();
        existingAppointment.setDoctorId(1L);
        // CORRECCIÓN: Usar toLocalDate() para obtener solo la fecha
        existingAppointment.setAppointmentDateTime(LocalDateTime.of(2025, 1, 20, 10, 0).toLocalDate());
        existingAppointment.setStartAt(LocalTime.of(10, 0));
        existingAppointment.setEndAt(LocalTime.of(10, 45)); // El servicio calculará esto, pero lo ponemos para claridad

        // 2. Nueva cita que se intenta crear y que entra en conflicto con la existente
        AppointmentModel newAppointment = new AppointmentModel();
        newAppointment.setDoctorId(1L);
        newAppointment.setPatientId(2L);
        // CORRECCIÓN: Usar toLocalDate() para obtener solo la fecha
        newAppointment.setAppointmentDateTime(LocalDateTime.of(2025, 1, 20, 10, 30).toLocalDate());
        newAppointment.setStartAt(LocalTime.of(10, 30));

        // 3. Simular el comportamiento del repositorio: cuando se busque citas para ese doctor y fecha, devolver la cita existente
        when(appointmentRepository.findByDoctorIdAndDate(1L, LocalDate.of(2025, 1, 20)))
                .thenReturn(List.of(existingAppointment));

        // Act & Assert (Actuar y Afirmar)

        // Se espera que 'create' lance una InvalidAppointmentException porque hay un conflicto de horario
        assertThrows(
                InvalidAppointmentException.class,
                () -> service.create(newAppointment),
                "Debería lanzarse una excepción por conflicto de horario"
        );
    }
}
