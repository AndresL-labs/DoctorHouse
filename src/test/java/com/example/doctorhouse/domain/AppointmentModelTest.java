package com.example.doctorhouse.domain;

import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.model.enums.AppointmentStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppointmentModelTest {

    // The time conflict logic has been moved to AppointmentService.
    // A test for that behavior should be created in an AppointmentServiceTest class.

    @Test
    void shouldChangeStatusToCanceled() {
        // Arrange
        AppointmentModel appointment = new AppointmentModel();
        appointment.setStatus(AppointmentStatus.SCHEDULED);

        // Act
        // The business logic for canceling (e.g., a cancel() method) now resides in a service.
        // This test now simply verifies that the model's status property can be changed.
        appointment.setStatus(AppointmentStatus.CANCELED);

        // Assert
        assertEquals(AppointmentStatus.CANCELED, appointment.getStatus());
    }
}
