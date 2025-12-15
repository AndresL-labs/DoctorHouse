package com.example.doctorhouse.domain.port.in;

import com.example.doctorhouse.domain.model.Patient;

public interface RegisterPatientUseCase {
    Patient registerPatient(Patient patient);
}
