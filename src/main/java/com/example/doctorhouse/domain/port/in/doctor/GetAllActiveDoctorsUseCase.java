package com.example.doctorhouse.domain.port.in.doctor;

import com.example.doctorhouse.domain.model.DoctorModel;

import java.util.List;

public interface GetAllActiveDoctorsUseCase {
    List<DoctorModel> execute();
}
