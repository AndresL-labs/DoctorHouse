package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.AppointmentWithPatient;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ListDoctorAppointmentsServiceTest {

    @Mock
    private AppointmentRepositoryPort appointmentRepositoryPort;

    @InjectMocks
    private ListDoctorAppointmentsService listDoctorAppointmentsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldGetTodaysAppointments() {
        String doctorEmail = "doc@test.com";
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().atTime(LocalTime.MAX);
        List<AppointmentWithPatient> expectedAppointments = List.of(new AppointmentWithPatient());

        when(appointmentRepositoryPort.findByDoctorEmailAndDateRange(eq(doctorEmail), any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(expectedAppointments);

        List<AppointmentWithPatient> result = listDoctorAppointmentsService.getTodaysAppointments(doctorEmail);

        assertEquals(expectedAppointments, result);
        verify(appointmentRepositoryPort).findByDoctorEmailAndDateRange(eq(doctorEmail), any(LocalDateTime.class),
                any(LocalDateTime.class));
    }

    @Test
    void shouldGetTomorrowsAppointments() {
        String doctorEmail = "doc@test.com";
        LocalDateTime startOfDay = LocalDate.now().plusDays(1).atStartOfDay();
        LocalDateTime endOfDay = LocalDate.now().plusDays(1).atTime(LocalTime.MAX);
        List<AppointmentWithPatient> expectedAppointments = List.of(new AppointmentWithPatient());

        when(appointmentRepositoryPort.findByDoctorEmailAndDateRange(eq(doctorEmail), any(LocalDateTime.class),
                any(LocalDateTime.class)))
                .thenReturn(expectedAppointments);

        List<AppointmentWithPatient> result = listDoctorAppointmentsService.getTomorrowsAppointments(doctorEmail);

        assertEquals(expectedAppointments, result);
        verify(appointmentRepositoryPort).findByDoctorEmailAndDateRange(eq(doctorEmail), any(LocalDateTime.class),
                any(LocalDateTime.class));
    }
}
