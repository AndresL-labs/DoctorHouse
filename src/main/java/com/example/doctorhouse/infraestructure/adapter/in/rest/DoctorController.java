package com.example.doctorhouse.infraestructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.DoctorModel;
import com.example.doctorhouse.domain.port.in.doctor.GetAllActiveDoctorsUseCase;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto.DoctorResponseDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final GetAllActiveDoctorsUseCase getAllActiveDoctorsUseCase;

    public DoctorController(GetAllActiveDoctorsUseCase getAllActiveDoctorsUseCase) {
        this.getAllActiveDoctorsUseCase = getAllActiveDoctorsUseCase;
    }

    @GetMapping("/active")
    public ResponseEntity<List<DoctorResponseDTO>> getActiveDoctors() {
        List<DoctorModel> doctors = getAllActiveDoctorsUseCase.execute();
        List<DoctorResponseDTO> response = doctors.stream()
                .map(doctor -> new DoctorResponseDTO(doctor.getId(), doctor.getName()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
}
