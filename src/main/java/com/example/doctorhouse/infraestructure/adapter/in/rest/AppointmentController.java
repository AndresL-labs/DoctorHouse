package com.example.doctorhouse.infraestructure.adapter.in.rest;

import com.example.doctorhouse.application.mapper.AppointmentMapperDTO;
import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import com.example.doctorhouse.domain.port.in.appointment.CreateAppointmentUseCase;
import com.example.doctorhouse.domain.port.in.appointment.ScheduleAppointmentUseCase;
import com.example.doctorhouse.domain.port.in.doctor.GetDoctorAvailabilityUseCase;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto.AppointmentAvailabilityResponseDTO;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto.AppointmentRequestDTO;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto.AppointmentResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final CreateAppointmentUseCase createAppointmentUseCase;
    private final ScheduleAppointmentUseCase scheduleAppointmentUseCase;
    private final GetDoctorAvailabilityUseCase availabilityUseCase;

    public AppointmentController(CreateAppointmentUseCase createAppointmentUseCase,
                                 ScheduleAppointmentUseCase scheduleAppointmentUseCase,
                                 GetDoctorAvailabilityUseCase availabilityUseCase) {
        this.createAppointmentUseCase = createAppointmentUseCase;
        this.scheduleAppointmentUseCase = scheduleAppointmentUseCase;
        this.availabilityUseCase = availabilityUseCase;
    }

    // ===============================
    // CREATE APPOINTMENT
    // ===============================
    @PostMapping
    public ResponseEntity<AppointmentResponseDTO> create(
            @Valid @RequestBody AppointmentRequestDTO request
    ) {

        AppointmentModel model = new AppointmentModel();
        model.setPatientId(request.getPatientId());
        model.setDoctorId(request.getDoctorId());
        model.setAppointmentDateTime(request.getStartDateTime());
        model.setStartAt(request.getStartDateTime().toLocalTime());
        model.setStatus(AppointmentStatus.SCHEDULED);
        model.setCreatedAt(LocalDateTime.now());

        AppointmentModel saved =
                createAppointmentUseCase.create(model);

        AppointmentResponseDTO response =
                AppointmentMapperDTO.toResponse(saved);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    @PostMapping("/schedule")
    public ResponseEntity<AppointmentModel> schedule(
            @RequestParam Long patientId,
            @RequestParam Long doctorId,
            @RequestParam String date,
            @RequestParam String time
    ) {

        LocalDateTime startDateTime =
                LocalDateTime.of(
                        LocalDate.parse(date),
                        LocalTime.parse(time)
                );

        return ResponseEntity.ok(
                scheduleAppointmentUseCase.execute(
                        patientId,
                        doctorId,
                        startDateTime
                )
        );
    }

    @GetMapping("/availability")
    public AppointmentAvailabilityResponseDTO getAvailability(
            @RequestParam Long doctorId,
            @RequestParam LocalDate date
    ) {

        AppointmentAvailabilityResponseDTO dto =
                new AppointmentAvailabilityResponseDTO();

        dto.setDoctorId(doctorId);
        dto.setDate(date);
        dto.setAvailableTimes(
                availabilityUseCase.execute(doctorId, date)
        );

        return dto;
    }
}