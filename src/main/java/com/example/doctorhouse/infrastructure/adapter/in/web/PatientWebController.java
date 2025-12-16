package com.example.doctorhouse.infrastructure.adapter.in.web;

import com.example.doctorhouse.domain.port.in.CheckAvailabilityUseCase;
import com.example.doctorhouse.domain.port.in.ListDoctorsUseCase;
import com.example.doctorhouse.domain.port.in.ScheduleAppointmentUseCase;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collections;

@Controller
@RequestMapping("/patient")
@RequiredArgsConstructor
public class PatientWebController {

    private final ListDoctorsUseCase listDoctorsUseCase;
    private final CheckAvailabilityUseCase checkAvailabilityUseCase;
    private final ScheduleAppointmentUseCase scheduleAppointmentUseCase;
    private final UserRepositoryPort userRepositoryPort;

    @GetMapping("/schedule-appointment")
    public String scheduleAppointment(Model model) {
        model.addAttribute("doctors", listDoctorsUseCase.findAllDoctors());
        return "patient/schedule_appointment";
    }

    @GetMapping("/schedule-item/availability")
    @ResponseBody
    public ResponseEntity<?> getAvailability(@RequestParam Long doctorId, @RequestParam String date) {
        try {
            return ResponseEntity.ok(checkAvailabilityUseCase.getAvailableSlots(doctorId, LocalDate.parse(date)));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/schedule-appointment")
    public String bookAppointment(@RequestParam Long doctorId, @RequestParam String date, @RequestParam String time,
            Authentication authentication, Model model) {
        try {
            String patientEmail = authentication.getName();
            String patientDni = userRepositoryPort.findByEmail(patientEmail)
                    .map(user -> user.getDni())
                    .orElseThrow(() -> new IllegalStateException("User not found"));

            LocalDateTime dateTime = LocalDateTime.of(LocalDate.parse(date), LocalTime.parse(time));
            scheduleAppointmentUseCase.scheduleAppointment(doctorId, patientDni, dateTime);
            return "redirect:/patient/schedule-appointment?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("doctors", listDoctorsUseCase.findAllDoctors());
            return "patient/schedule_appointment";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while scheduling the appointment.");
            model.addAttribute("doctors", listDoctorsUseCase.findAllDoctors());
            return "patient/schedule_appointment";
        }
    }
}
