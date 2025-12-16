package com.example.doctorhouse.infrastructure.adapter.in.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@lombok.RequiredArgsConstructor
public class DashboardController {

    private final com.example.doctorhouse.domain.port.in.ListDoctorAppointmentsUseCase listDoctorAppointmentsUseCase;
    private final com.example.doctorhouse.domain.port.in.ListPatientAppointmentsUseCase listPatientAppointmentsUseCase;
    private final com.example.doctorhouse.domain.port.in.ListAllScheduledAppointmentsUseCase listAllScheduledAppointmentsUseCase;

    private final com.example.doctorhouse.domain.port.out.UserRepositoryPort userRepositoryPort;
    private final com.example.doctorhouse.domain.port.out.PatientRepositoryPort patientRepositoryPort;

    @GetMapping("/home")
    public String home(Authentication authentication) {
        if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_MEDICO"))) {
            return "redirect:/doctor/home";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PACIENTE"))) {
            return "redirect:/patient/home";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANALISTA"))) {
            return "redirect:/analyst/home";
        } else if (authentication.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            return "redirect:/admin/home";
        }
        return "redirect:/login?error"; // Redirect to login if no role matches, instead of unmapped /
    }

    private String getUserName(String email) {
        return userRepositoryPort.findByEmail(email)
                .map(user -> user.getFirstName() + " " + user.getLastName())
                .orElse(email);
    }

    @GetMapping("/doctor/home")
    public String doctorHome(Model model, Authentication authentication) {
        model.addAttribute("username", getUserName(authentication.getName()));
        model.addAttribute("appointments",
                listDoctorAppointmentsUseCase.getTodaysAppointments(authentication.getName()));
        model.addAttribute("tomorrowsAppointments",
                listDoctorAppointmentsUseCase.getTomorrowsAppointments(authentication.getName()));
        return "doctor/home";
    }

    @GetMapping("/patient/home")
    public String patientHome(Model model, Authentication authentication) {
        model.addAttribute("username", getUserName(authentication.getName()));
        model.addAttribute("appointmentsGrouped",
                listPatientAppointmentsUseCase.getAppointmentsGrouped(authentication.getName()));
        return "patient/home";
    }

    @GetMapping("/analyst/home")
    public String analystHome(Model model, Authentication authentication) {
        model.addAttribute("username", getUserName(authentication.getName()));

        java.util.List<com.example.doctorhouse.domain.model.Appointment> appointments = listAllScheduledAppointmentsUseCase
                .execute();
        model.addAttribute("appointments", appointments);

        java.util.Map<Long, String> patientDnis = new java.util.HashMap<>();
        for (com.example.doctorhouse.domain.model.Appointment app : appointments) {
            if (app.getPatientId() != null && !patientDnis.containsKey(app.getPatientId())) {
                patientRepositoryPort.findById(app.getPatientId())
                        .ifPresent(p -> patientDnis.put(app.getPatientId(), p.getDni()));
            }
        }
        model.addAttribute("patientDnis", patientDnis);

        return "analyst/home";
    }

    @GetMapping("/admin/home")
    public String adminHome(Model model, Authentication authentication) {
        model.addAttribute("username", authentication.getName());
        return "admin/home";
    }
}
