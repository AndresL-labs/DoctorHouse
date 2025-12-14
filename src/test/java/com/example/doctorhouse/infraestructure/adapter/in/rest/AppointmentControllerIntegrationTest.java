package com.example.doctorhouse.infraestructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.model.DoctorModel;
import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AppointmentControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppointmentRepositoryPort appointmentRepositoryPort;

    @MockBean
    private DoctorRepositoryPort doctorRepositoryPort;

    private DoctorModel activeDoctor;
    private DoctorModel inactiveDoctor;

    @BeforeEach
    void setUp() {
        activeDoctor = new DoctorModel(1L, "Dr. Activo", true);
        inactiveDoctor = new DoctorModel(2L, "Dr. Inactivo", false);
    }

    @Test
    void testScheduleValidAppointment_ShouldCreateAndReturnScheduled() throws Exception {
        // Given: Un médico activo y sin citas existentes
        Long patientId = 100L;
        Long doctorId = activeDoctor.getId();
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.of(9, 0);

        when(doctorRepositoryPort.isDoctorActive(doctorId)).thenReturn(true);
        when(appointmentRepositoryPort.findByDoctorIdAndDate(any(), any())).thenReturn(Collections.emptyList());

        // Mock del guardado
        when(appointmentRepositoryPort.save(any(AppointmentModel.class))).thenAnswer(invocation -> {
            AppointmentModel saved = invocation.getArgument(0);
            saved.setIdAppointment(1L); // Simular ID generado por la BD
            saved.setStatus(AppointmentStatus.SCHEDULED);
            return saved;
        });

        // When & Then: Se agenda una cita válida
        mockMvc.perform(post("/appointments/schedule")
                        .param("patientId", String.valueOf(patientId))
                        .param("doctorId", String.valueOf(doctorId))
                        .param("date", date.toString())
                        .param("time", time.toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.status", is("SCHEDULED")))
                .andExpect(jsonPath("$.doctorId", is(doctorId.intValue())))
                .andExpect(jsonPath("$.patientId", is(patientId.intValue())));
    }

    @Test
    void testScheduleWithInactiveDoctor_ShouldReturnError() throws Exception {
        // Given: Un médico inactivo
        Long patientId = 100L;
        Long doctorId = inactiveDoctor.getId();
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime time = LocalTime.of(9, 0);

        when(doctorRepositoryPort.isDoctorActive(doctorId)).thenReturn(false);

        // When & Then: Se intenta agendar con el médico inactivo
        mockMvc.perform(post("/appointments/schedule")
                        .param("patientId", String.valueOf(patientId))
                        .param("doctorId", String.valueOf(doctorId))
                        .param("date", date.toString())
                        .param("time", time.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("El médico seleccionado no está activo")));
    }

    @Test
    void testScheduleConflictingAppointment_ShouldReturnError() throws Exception {
        // Given: Un médico activo con una cita existente que genera conflicto
        Long patientId = 100L;
        Long doctorId = activeDoctor.getId();
        LocalDate date = LocalDate.now().plusDays(1);
        LocalTime newAppointmentTime = LocalTime.of(9, 0);

        // Cita existente a las 9:00, dura 45 min. Con el buffer de 60 min, bloquea hasta las 10:45.
        AppointmentModel existingAppointment = new AppointmentModel(
                1L, 101L, doctorId, date, LocalTime.of(9, 0), AppointmentStatus.SCHEDULED, LocalDateTime.now()
        );
        existingAppointment.setEndAt(LocalTime.of(9, 45));

        when(doctorRepositoryPort.isDoctorActive(doctorId)).thenReturn(true);
        when(appointmentRepositoryPort.findByDoctorIdAndDate(doctorId, date)).thenReturn(List.of(existingAppointment));

        // When & Then: Se intenta agendar una cita que choca con la existente
        mockMvc.perform(post("/appointments/schedule")
                        .param("patientId", String.valueOf(patientId))
                        .param("doctorId", String.valueOf(doctorId))
                        .param("date", date.toString())
                        .param("time", newAppointmentTime.toString()))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("El médico no tiene disponibilidad en ese horario")));
    }

    @Test
    void testGetActiveDoctors_ShouldOnlyReturnActive() throws Exception {
        // Given: Una lista de médicos con activos e inactivos
        when(doctorRepositoryPort.findAllActiveDoctors()).thenReturn(List.of(activeDoctor));

        // When & Then: Se pide la lista de médicos activos
        mockMvc.perform(get("/doctors/active"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()", is(1)))
                .andExpect(jsonPath("$[0].id", is(activeDoctor.getId().intValue())))
                .andExpect(jsonPath("$[0].name", is(activeDoctor.getName())));
    }

    @Test
    void testGetAvailability_ShouldNotShowOccupiedSlots() throws Exception {
        // Given: Una cita existente a las 9:00
        Long doctorId = activeDoctor.getId();
        LocalDate date = LocalDate.now().plusDays(1);
        AppointmentModel existingAppointment = new AppointmentModel(
                1L, 101L, doctorId, date, LocalTime.of(9, 0), AppointmentStatus.SCHEDULED, LocalDateTime.now()
        );
        existingAppointment.setEndAt(LocalTime.of(9, 45));

        when(appointmentRepositoryPort.findByDoctorIdAndDate(doctorId, date)).thenReturn(List.of(existingAppointment));

        // When & Then: Se pide la disponibilidad
        // La lógica en DoctorService debe excluir 9:00 y cualquier otro slot que se solape
        // con el rango 9:00 - 10:45 (cita + buffer)
        mockMvc.perform(get("/appointments/availability")
                        .param("doctorId", String.valueOf(doctorId))
                        .param("date", date.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.availableTimes.length()", is(10))) // Asumiendo 12 slots totales - 2 slots ocupados (9:00 y 9:45)
                .andExpect(jsonPath("$.availableTimes[0]", is("08:00")))
                .andExpect(jsonPath("$.availableTimes[1]", is("08:45")))
                .andExpect(jsonPath("$.availableTimes[2]", is("10:30"))); // El siguiente slot libre después del conflicto
    }
}
