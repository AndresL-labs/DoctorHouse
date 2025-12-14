package com.example.doctorhouse.domain.port.out;

import com.example.doctorhouse.domain.model.DoctorModel;

import java.util.List;

// Puerto de salida para médicos
public interface DoctorRepositoryPort {
    boolean isDoctorActive(Long doctorId);
    List<DoctorModel> findAllActiveDoctors();
}
