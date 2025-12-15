package com.example.doctorhouse.infrastructure.adapter.in.web;

import com.example.doctorhouse.domain.model.Doctor;
import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.model.enums.UserRole;
import com.example.doctorhouse.domain.port.in.ListDoctorsUseCase;
import com.example.doctorhouse.domain.port.in.ManageDoctorsUseCase;
import com.example.doctorhouse.domain.port.in.RegisterDoctorUseCase;
import com.example.doctorhouse.domain.port.in.RegisterUserUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminWebController {

    private final RegisterDoctorUseCase registerDoctorUseCase;
    private final RegisterUserUseCase registerUserUseCase;
    private final ManageDoctorsUseCase manageDoctorsUseCase;
    private final ListDoctorsUseCase listDoctorsUseCase;

    // === Dashboard ===
    // Note: DashboardController handles /admin/home, but we might want to override
    // or enrich it here?
    // Actually, DashboardController is generic. If we want detailed cards, it's
    // just a view change.
    // Ideally DashboardController redirects here?
    // DashboardController currently renders 'admin/home'.
    // If we want to keep it simple, we modify admin/home.html.
    // But if we want specific data on the home page, we might need to handle it.
    // DashboardController handles /admin/home. I will leave it there.

    // === Register Doctor ===
    @GetMapping("/register-doctor")
    public String registerDoctorForm(Model model) {
        model.addAttribute("doctor", new Doctor());
        return "admin/register_doctor";
    }

    @PostMapping("/register-doctor")
    public String registerDoctor(@ModelAttribute Doctor doctor, Model model) {
        doctor.setActive(true);
        doctor.setRole(UserRole.MEDICO);
        try {
            registerDoctorUseCase.registerDoctor(doctor);
            return "redirect:/admin/register-doctor?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/register_doctor";
        }
    }

    // === Register Admin/Analyst ===
    @GetMapping("/register-user")
    public String registerUserForm(Model model) {
        model.addAttribute("user", new User());
        return "admin/register_user";
    }

    @PostMapping("/register-user")
    public String registerUser(@ModelAttribute User user, Model model) {
        // Role is bound from form
        user.setActive(true);
        try {
            registerUserUseCase.registerUser(user);
            return "redirect:/admin/register-user?success";
        } catch (IllegalArgumentException e) {
            model.addAttribute("error", e.getMessage());
            return "admin/register_user";
        }
    }

    // === Manage Doctors ===
    @GetMapping("/doctors")
    public String listDoctors(Model model) {
        List<Doctor> allDoctors = listDoctorsUseCase.findAllDoctors();

        Map<Boolean, List<Doctor>> groups = allDoctors.stream()
                .collect(Collectors.partitioningBy(d -> Boolean.TRUE.equals(d.getActive())));

        model.addAttribute("activeDoctors", groups.get(true));
        model.addAttribute("inactiveDoctors", groups.get(false));

        return "admin/doctors";
    }

    @PostMapping("/doctors/{id}/activate")
    public String activateDoctor(@PathVariable Long id) {
        manageDoctorsUseCase.activateDoctor(id);
        return "redirect:/admin/doctors";
    }

    @PostMapping("/doctors/{id}/deactivate")
    public String deactivateDoctor(@PathVariable Long id) {
        manageDoctorsUseCase.deactivateDoctor(id);
        return "redirect:/admin/doctors";
    }
}
