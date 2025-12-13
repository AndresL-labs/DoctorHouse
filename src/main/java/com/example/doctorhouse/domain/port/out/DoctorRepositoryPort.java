package com.example.doctorhouse.domain.port.out;

// Puerto de salida para médicos
public interface DoctorRepositoryPort {
    boolean isDoctorActive(Long doctorId);
}
