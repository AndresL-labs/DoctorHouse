package com.example.doctorhouse.infrastructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.model.enums.UserRole;
import com.example.doctorhouse.domain.port.in.RegisterDoctorUseCase;
import com.example.doctorhouse.domain.port.in.RegisterPatientUseCase;
import com.example.doctorhouse.domain.port.in.RegisterUserUseCase;
import com.example.doctorhouse.infrastructure.adapter.in.mapper.DoctorMapper;
import com.example.doctorhouse.infrastructure.adapter.in.mapper.PatientMapper;
import com.example.doctorhouse.infrastructure.adapter.in.mapper.UserMapper;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.DoctorRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.DoctorResponse;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.PatientRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.PatientResponse;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.UserRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = { DoctorController.class, PatientController.class, UserController.class })
@org.springframework.context.annotation.Import(com.example.doctorhouse.infrastructure.config.security.SecurityConfig.class)
class ControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private RegisterDoctorUseCase registerDoctorUseCase;
    @MockBean
    private RegisterPatientUseCase registerPatientUseCase;
    @MockBean
    private RegisterUserUseCase registerUserUseCase;

    // We need to mock the Mappers because they are used in the controller.
    // Since they are interfaces with MapStruct implementation generated at compile
    // time,
    // if we use @WebMvcTest we might need to mock them or include the generated
    // impl in context.
    // However, @MockBean is easier here.
    @MockBean
    private DoctorMapper doctorMapper;
    @MockBean
    private PatientMapper patientMapper;
    @MockBean
    private UserMapper userMapper;

    @MockBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @MockBean
    private com.example.doctorhouse.infrastructure.config.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws Exception {
        org.mockito.Mockito.doAnswer(invocation -> {
            jakarta.servlet.ServletRequest req = invocation.getArgument(0);
            jakarta.servlet.ServletResponse res = invocation.getArgument(1);
            jakarta.servlet.FilterChain chain = invocation.getArgument(2);
            chain.doFilter(req, res);
            return null;
        }).when(jwtAuthenticationFilter).doFilter(any(), any(), any());
    }

    @Test
    void shouldCreateDoctor() throws Exception {
        DoctorRequest request = new DoctorRequest();
        request.setFirstName("Gregory");
        request.setLastName("House");
        request.setEmail("house@doctorhouse.com");
        request.setPassword("123456");
        request.setDni("MED001");
        request.setLicenseNumber("LIC-001");
        request.setSpecialty("Diagnostician");

        when(doctorMapper.toDomain(any())).thenReturn(new Doctor());
        when(registerDoctorUseCase.registerDoctor(any())).thenReturn(new Doctor());
        when(doctorMapper.toResponse(any())).thenReturn(new DoctorResponse());

        mockMvc.perform(post("/api/doctors")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreatePatient() throws Exception {
        PatientRequest request = new PatientRequest();
        request.setFirstName("Alice");
        request.setLastName("Wonderland");
        request.setEmail("alice@wonderland.com");
        request.setPassword("123456");
        request.setDni("PAT001");
        request.setBirthDate(LocalDate.now().minusYears(20));

        when(patientMapper.toDomain(any())).thenReturn(new Patient());
        when(registerPatientUseCase.registerPatient(any())).thenReturn(new Patient());
        when(patientMapper.toResponse(any())).thenReturn(new PatientResponse());

        mockMvc.perform(post("/api/patients")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }

    @Test
    void shouldCreateUser() throws Exception {
        UserRequest request = new UserRequest();
        request.setFirstName("Admin");
        request.setLastName("System");
        request.setEmail("admin@doctorhouse.com");
        request.setPassword("123456");
        request.setDni("ADM001");
        request.setRole(UserRole.ADMIN);

        when(userMapper.toDomain(any())).thenReturn(new User());
        when(registerUserUseCase.registerUser(any())).thenReturn(new User());
        when(userMapper.toResponse(any())).thenReturn(new UserResponse());

        mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated());
    }
}
