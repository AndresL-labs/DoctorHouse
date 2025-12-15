package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.Doctor;

public interface RegisterDoctorUseCase {
    Doctor registerDoctor(Doctor doctor);
}
