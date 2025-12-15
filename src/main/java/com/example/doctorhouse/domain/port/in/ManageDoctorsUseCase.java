package com.example.doctorhouse.domain.port.in;

public interface ManageDoctorsUseCase {
    void activateDoctor(Long id);

    void deactivateDoctor(Long id);
}
