package com.example.doctorhouse.infrastructure.config;

import com.example.doctorhouse.application.service.AppointmentService;
import com.example.doctorhouse.application.service.LoginService;
import com.example.doctorhouse.application.service.RegisterDoctorService;
import com.example.doctorhouse.application.service.RegisterPatientService;
import com.example.doctorhouse.application.service.RegisterUserService;
import com.example.doctorhouse.domain.port.in.CreateAppointmentUseCase;
import com.example.doctorhouse.domain.port.in.LoginUseCase;
import com.example.doctorhouse.domain.port.in.RegisterDoctorUseCase;
import com.example.doctorhouse.domain.port.in.RegisterPatientUseCase;
import com.example.doctorhouse.domain.port.in.RegisterUserUseCase;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import com.example.doctorhouse.domain.port.out.PatientRepositoryPort;
import com.example.doctorhouse.domain.port.out.TokenProviderPort;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansConfig {
    @Bean
    CreateAppointmentUseCase createAppointmentUseCase(AppointmentRepositoryPort appointmentRepository) {
        return new AppointmentService(appointmentRepository);
    }

    @Bean
    RegisterDoctorUseCase registerDoctorUseCase(DoctorRepositoryPort doctorRepository,
            PasswordEncoder passwordEncoder) {
        return new RegisterDoctorService(doctorRepository, passwordEncoder);
    }

    @Bean
    public RegisterUserService registerUserService(UserRepositoryPort userRepositoryPort,
            PasswordEncoder passwordEncoder) {
        return new RegisterUserService(userRepositoryPort, passwordEncoder);
    }

    @Bean
    public LoginUseCase loginUseCase(UserRepositoryPort userRepositoryPort, TokenProviderPort tokenProviderPort,
            PasswordEncoder passwordEncoder) {
        return new LoginService(userRepositoryPort, tokenProviderPort, passwordEncoder);
    }

    @Bean
    RegisterPatientUseCase registerPatientUseCase(PatientRepositoryPort patientRepositoryPort,
            PasswordEncoder passwordEncoder) {
        return new RegisterPatientService(patientRepositoryPort, passwordEncoder);
    }
}
