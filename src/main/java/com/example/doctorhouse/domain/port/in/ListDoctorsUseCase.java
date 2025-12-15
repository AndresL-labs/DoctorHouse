package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.Doctor;

import java.util.List;

public interface ListDoctorsUseCase {
    List<Doctor> findAllDoctors();
}
