package com.example.doctorhouse.infrastructure.adapter.in.web;

import com.example.doctorhouse.domain.port.in.CancelAppointmentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentWebController {

    private final CancelAppointmentUseCase cancelAppointmentUseCase;
    private final com.example.doctorhouse.domain.port.in.GetMedicalRecordUseCase getMedicalRecordUseCase;

    @PostMapping("/{id}/cancel")
    public String cancelAppointment(@PathVariable Long id, Authentication authentication) {
        cancelAppointmentUseCase.cancelAppointment(id, authentication.getName());

        if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ANALISTA"))) {
            return "redirect:/analyst/home?cancelled";
        } else if (authentication.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_PACIENTE"))) {
            return "redirect:/patient/home?cancelled";
        }

        return "redirect:/home?cancelled";
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}/report")
    public String viewMedicalRecord(@PathVariable Long id, org.springframework.ui.Model model) {
        return getMedicalRecordUseCase.getMedicalRecord(id)
                .map(record -> {
                    model.addAttribute("record", record);
                    return "patient/medical_record";
                })
                .orElse("redirect:/patient/home?no_record");
    }

    private final com.example.doctorhouse.domain.port.in.FinishAppointmentUseCase finishAppointmentUseCase;

    @org.springframework.web.bind.annotation.GetMapping("/{id}/finish")
    public String showFinishAppointmentForm(@PathVariable Long id, org.springframework.ui.Model model) {
        model.addAttribute("appointmentId", id);
        model.addAttribute("medicalRecord", new com.example.doctorhouse.domain.model.MedicalRecord());
        return "doctor/finish_appointment";
    }

    @PostMapping("/{id}/finish")
    public String finishAppointment(@PathVariable Long id,
            @org.springframework.web.bind.annotation.ModelAttribute com.example.doctorhouse.domain.model.MedicalRecord medicalRecord) {
        finishAppointmentUseCase.finishAppointment(id, medicalRecord);
        return "redirect:/doctor/home?finished";
    }
}
