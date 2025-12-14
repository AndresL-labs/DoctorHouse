package com.example.doctorhouse.infraestructure.exception;

import com.example.doctorhouse.domain.exception.InvalidAppointmentException;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.dto.ErrorResponseDTO;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InvalidAppointmentException.class)
    public ResponseEntity<ErrorResponseDTO> handleAppointmentException(
            InvalidAppointmentException ex
    ) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDTO> handleValidationErrors(
            MethodArgumentNotValidException ex
    ) {
        String message = ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO(message));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolation(
            DataIntegrityViolationException ex
    ) {
        // Comprobar si el mensaje de error contiene el nombre de nuestra restricción de unicidad
        if (ex.getMostSpecificCause().getMessage().contains("unique_doctor_time_constraint")) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT) // 409 Conflict
                    .body(new ErrorResponseDTO("Ya existe una cita para este médico en el horario seleccionado."));
        }
        // Para otras violaciones de integridad (como claves foráneas), devolvemos un Bad Request
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponseDTO("Error de integridad de datos: " + ex.getMostSpecificCause().getMessage()));
    }
}
