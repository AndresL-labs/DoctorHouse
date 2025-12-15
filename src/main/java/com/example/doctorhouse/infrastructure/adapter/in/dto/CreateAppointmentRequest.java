package com.example.doctorhouse.infrastructure.adapter.in.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequest {

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long patientId;

    @NotNull(message = "El ID del médico es obligatorio")
    private Long doctorId;

    @NotNull(message = "La fecha y hora de la cita son obligatorias")
    @Future(message = "La cita debe programarse para una fecha futura")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime scheduledAt; // Mapea a 'scheduled_at'

    @NotNull(message = "La duración es obligatoria")
    @Positive(message = "La duración debe ser mayor a 0 minutos")
    private Integer duration; // Mapea a 'duration'
}