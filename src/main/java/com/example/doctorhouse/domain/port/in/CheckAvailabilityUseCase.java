package com.example.doctorhouse.domain.port.in;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public interface CheckAvailabilityUseCase {
    List<LocalTime> getAvailableSlots(Long doctorId, LocalDate date);
}
