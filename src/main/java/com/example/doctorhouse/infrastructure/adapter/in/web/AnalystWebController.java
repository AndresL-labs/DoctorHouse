package com.example.doctorhouse.infrastructure.adapter.in.web;

import com.example.doctorhouse.domain.model.Patient;
import com.example.doctorhouse.domain.port.in.CheckAvailabilityUseCase;
import com.example.doctorhouse.domain.port.in.ListDoctorsUseCase;
import com.example.doctorhouse.domain.port.in.RegisterPatientUseCase;
import com.example.doctorhouse.domain.port.in.ScheduleAppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
@RequestMapping("/analyst")
@RequiredArgsConstructor
public class AnalystWebController {

    private final com.example.doctorhouse.domain.port.in.RegisterPatientUseCase registerPatientUseCase;
    private final com.example.doctorhouse.domain.port.out.PatientRepositoryPort patientRepositoryPort;

    @GetMapping("/register-user")
    public String registerUser(org.springframework.ui.Model model) {
        model.addAttribute("patient", new com.example.doctorhouse.domain.model.Patient());
        return "analyst/register_user";
    }

    @org.springframework.web.bind.annotation.PostMapping("/register-user")
    public String processRegisterUser(
            @org.springframework.web.bind.annotation.ModelAttribute com.example.doctorhouse.domain.model.Patient patient,
            org.springframework.ui.Model model) {
        patient.setActive(true);
        patient.setCreatedAt(java.time.LocalDateTime.now());

        try {
            registerPatientUseCase.registerPatient(patient);
            return "redirect:/analyst/register-user?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            // We need to return the view name, not redirect, so we can pass the model.
            // But we also need to keep the form data (patient object).
            // 'patient' is already in the model because of @ModelAttribute.
            return "analyst/register_user";
        }
    }

    private final com.example.doctorhouse.domain.port.in.ListDoctorsUseCase listDoctorsUseCase;
    private final com.example.doctorhouse.domain.port.in.CheckAvailabilityUseCase checkAvailabilityUseCase;
    private final com.example.doctorhouse.domain.port.in.ScheduleAppointmentUseCase scheduleAppointmentUseCase;

    // ... (existing register user methods)

    @GetMapping("/schedule-appointment")
    public String scheduleAppointment(org.springframework.ui.Model model) {
        model.addAttribute("doctors", listDoctorsUseCase.findAllDoctors());
        return "analyst/schedule_appointment";
    }

    @GetMapping("/schedule-item/availability")
    @ResponseBody
    public org.springframework.http.ResponseEntity<?> getAvailability(@RequestParam Long doctorId,
            @RequestParam String date) {
        try {
            return org.springframework.http.ResponseEntity
                    .ok(checkAvailabilityUseCase.getAvailableSlots(doctorId, java.time.LocalDate.parse(date)));
        } catch (IllegalArgumentException e) {
            return org.springframework.http.ResponseEntity.badRequest()
                    .body(java.util.Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/schedule-appointment")
    public String bookAppointment(@RequestParam Long doctorId, @RequestParam String patientDni,
            @RequestParam String date, @RequestParam String time, Model model) {
        try {
            java.time.LocalDateTime dateTime = java.time.LocalDateTime.of(java.time.LocalDate.parse(date),
                    java.time.LocalTime.parse(time));
            scheduleAppointmentUseCase.scheduleAppointment(doctorId, patientDni, dateTime);
            return "redirect:/analyst/schedule-appointment?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("doctors", listDoctorsUseCase.findAllDoctors());
            return "analyst/schedule_appointment";
        } catch (Exception e) {
            model.addAttribute("error", "An error occurred while scheduling the appointment.");
            model.addAttribute("doctors", listDoctorsUseCase.findAllDoctors());
            return "analyst/schedule_appointment";
        }
    }

    @GetMapping("/patients/search")
    public String searchPatient(@RequestParam(required = false) String dni, Model model) {
        if (dni == null || dni.isEmpty()) {
            return "redirect:/analyst/home";
        }

        java.util.Optional<com.example.doctorhouse.domain.model.Patient> patientOpt = patientRepositoryPort
                .findByDni(dni);
        if (patientOpt.isPresent()) {
            model.addAttribute("patient", patientOpt.get());
            return "analyst/patient_details";
        } else {
            return "redirect:/analyst/home?error=PatientNotFound";
        }
    }
}
