package com.example.doctorhouse.infrastructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.domain.port.in.CreateAppointmentUseCase;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.AppointmentResponse;
import com.example.doctorhouse.infrastructure.adapter.in.rest.dto.CreateAppointmentRequest;
import com.example.doctorhouse.infrastructure.adapter.in.mapper.AppointmentRestMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final AppointmentRestMapper restMapper;

    @PostMapping
    public ResponseEntity<AppointmentResponse> create(
            @Valid @RequestBody CreateAppointmentRequest request) {

        Appointment domainIn = restMapper.toDomain(request);
        Appointment domainOut = createAppointmentUseCase.create(domainIn);
        AppointmentResponse response = restMapper.toResponse(domainOut);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}