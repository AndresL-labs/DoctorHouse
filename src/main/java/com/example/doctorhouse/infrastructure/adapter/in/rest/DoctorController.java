package com.example.doctorhouse.infrastructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.domain.port.in.RegisterDoctorUseCase;
import com.example.doctorhouse.infrastructure.adapter.in.mapper.DoctorMapper;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.DoctorRequest;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.DoctorResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/doctors")
@RequiredArgsConstructor
@Tag(name = "Doctor Management", description = "Endpoints for doctor registration and management")
public class DoctorController {

    private final RegisterDoctorUseCase registerDoctorUseCase;
    private final DoctorMapper doctorMapper;

    @Operation(summary = "Register a new Doctor", description = "Registers a new doctor in the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Doctor successfully registered"),
            @ApiResponse(responseCode = "400", description = "Invalid request data")
    })
    @PostMapping
    public ResponseEntity<DoctorResponse> register(@Valid @RequestBody DoctorRequest request) {
        Doctor domainDoctor = doctorMapper.toDomain(request);
        Doctor registeredDoctor = registerDoctorUseCase.registerDoctor(domainDoctor);
        return new ResponseEntity<>(doctorMapper.toResponse(registeredDoctor), HttpStatus.CREATED);
    }
}
