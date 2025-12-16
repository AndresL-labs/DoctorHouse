package com.example.doctorhouse.infrastructure.adapter.in.web;

import com.example.doctorhouse.domain.model.AppointmentWithPatient;
import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.port.in.ListAllScheduledAppointmentsUseCase;
import com.example.doctorhouse.domain.port.in.ListDoctorAppointmentsUseCase;
import com.example.doctorhouse.domain.port.in.ListPatientAppointmentsUseCase;
import com.example.doctorhouse.domain.port.out.PatientRepositoryPort;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import com.example.doctorhouse.infrastructure.config.security.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@WebMvcTest(DashboardController.class)
@Import(SecurityConfig.class)
class DashboardControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ListDoctorAppointmentsUseCase listDoctorAppointmentsUseCase;

        @MockBean
        private ListPatientAppointmentsUseCase listPatientAppointmentsUseCase;
        @MockBean
        private ListAllScheduledAppointmentsUseCase listAllScheduledAppointmentsUseCase;
        @MockBean
        private UserRepositoryPort userRepositoryPort;
        @MockBean
        private PatientRepositoryPort patientRepositoryPort;

        // Security mocks
        @MockBean
        private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;
        @MockBean
        private com.example.doctorhouse.infrastructure.config.security.JwtAuthenticationFilter jwtAuthenticationFilter;

        @Test
        @WithMockUser(username = "doc@test.com", roles = "MEDICO")
        void shouldShowDoctorHomeWithAppointments() throws Exception {
                User doctorUser = new User();
                doctorUser.setFirstName("Gregory");
                doctorUser.setLastName("House");

                when(userRepositoryPort.findByEmail(anyString())).thenReturn(Optional.of(doctorUser));
                AppointmentWithPatient appointment = new AppointmentWithPatient();
                appointment.setStatus(com.example.doctorhouse.domain.model.enums.AppointmentStatus.PROGRAMADA);

                when(listDoctorAppointmentsUseCase.getTodaysAppointments(anyString())).thenReturn(List.of(appointment));
                when(listDoctorAppointmentsUseCase.getTomorrowsAppointments(anyString()))
                                .thenReturn(List.of(appointment));

                // Stub the filter to continue execution
                org.mockito.Mockito.doAnswer(invocation -> {
                        jakarta.servlet.ServletRequest req = invocation.getArgument(0);
                        jakarta.servlet.ServletResponse res = invocation.getArgument(1);
                        jakarta.servlet.FilterChain chain = invocation.getArgument(2);
                        chain.doFilter(req, res);
                        return null;
                }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());

                mockMvc.perform(get("/doctor/home"))
                                .andExpect(status().isOk())
                                .andExpect(view().name("doctor/home"))
                                .andExpect(model().attributeExists("username"))
                                .andExpect(model().attributeExists("appointments"))
                                .andExpect(model().attributeExists("tomorrowsAppointments"));
        }
}
