package com.example.doctorhouse.infrastructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.domain.port.in.RegisterPatientUseCase;
import com.example.doctorhouse.infrastructure.adapter.in.mapper.PatientMapper;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.PatientRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.PatientResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/patients")
@RequiredArgsConstructor
public class PatientController {

    private final RegisterPatientUseCase registerPatientUseCase;
    private final PatientMapper patientMapper;

    @PostMapping
    public ResponseEntity<PatientResponse> register(@Valid @RequestBody PatientRequest request) {
        Patient domainPatient = patientMapper.toDomain(request);
        Patient registeredPatient = registerPatientUseCase.registerPatient(domainPatient);
        return new ResponseEntity<>(patientMapper.toResponse(registeredPatient), HttpStatus.CREATED);
    }
}
