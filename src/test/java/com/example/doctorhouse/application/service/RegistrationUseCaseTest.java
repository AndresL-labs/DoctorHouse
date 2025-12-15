package com.example.doctorhouse.application.service;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.model.enums.UserRole;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import com.example.doctorhouse.domain.port.out.PatientRepositoryPort;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RegistrationUseCaseTest {

    @Mock
    private DoctorRepositoryPort doctorRepositoryPort;
    @Mock
    private PatientRepositoryPort patientRepositoryPort;
    @Mock
    private UserRepositoryPort userRepositoryPort;
    @Mock
    private PasswordEncoder passwordEncoder;

    private RegisterDoctorService registerDoctorService;
    private RegisterPatientService registerPatientService;
    private RegisterUserService registerUserService;

    private AutoCloseable closeable;

    @BeforeEach
    void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
        registerDoctorService = new RegisterDoctorService(doctorRepositoryPort, passwordEncoder);
        registerPatientService = new RegisterPatientService(patientRepositoryPort, passwordEncoder);
        registerUserService = new RegisterUserService(userRepositoryPort, passwordEncoder);
    }

    @AfterEach
    void tearDown() throws Exception {
        closeable.close();
    }

    @Test
    void shouldRegisterDoctorSuccessfully() {
        Doctor doctor = new Doctor();
        doctor.setEmail("doctor@test.com");
        doctor.setLicenseNumber("LIC-123");
        doctor.setPassword("password");

        when(doctorRepositoryPort.findByEmail(any())).thenReturn(Optional.empty());
        when(doctorRepositoryPort.findByLicenseNumber(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(doctorRepositoryPort.save(any(Doctor.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Doctor registered = registerDoctorService.registerDoctor(doctor);

        assertNotNull(registered.getCreatedAt());
        verify(doctorRepositoryPort).save(doctor);
    }

    @Test
    void shouldThrowExceptionWhenDoctorEmailExists() {
        Doctor doctor = new Doctor();
        doctor.setEmail("existing@test.com");

        when(doctorRepositoryPort.findByEmail("existing@test.com")).thenReturn(Optional.of(new Doctor()));

        assertThrows(IllegalArgumentException.class, () -> registerDoctorService.registerDoctor(doctor));
    }

    @Test
    void shouldRegisterPatientSuccessfully() {
        Patient patient = new Patient();
        patient.setEmail("patient@test.com");
        patient.setDni("12345678");
        patient.setPassword("password");

        when(patientRepositoryPort.findByEmail(any())).thenReturn(Optional.empty());
        when(patientRepositoryPort.findByDni(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(patientRepositoryPort.save(any(Patient.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Patient registered = registerPatientService.registerPatient(patient);

        assertNotNull(registered.getCreatedAt());
        verify(patientRepositoryPort).save(patient);
    }

    @Test
    void shouldRegisterUserSuccessfully() {
        User user = new User();
        user.setEmail("admin@test.com");
        user.setRole(UserRole.ADMIN);
        user.setDni("12345678");
        user.setPassword("password");

        when(userRepositoryPort.findByEmail(any())).thenReturn(Optional.empty());
        when(userRepositoryPort.findByDni(any())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepositoryPort.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        User registered = registerUserService.registerUser(user);

        assertNotNull(registered.getCreatedAt());
        verify(userRepositoryPort).save(user);
    }
}
