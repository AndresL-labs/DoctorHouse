package com.example.doctorhouse.infraestructure.adapter.in.rest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController {

    @GetMapping("/schedule-appointment")
    public String scheduleAppointment() {
        return "appointment-scheduler"; // Esto resolverá a src/main/resources/templates/appointment-scheduler.html
    }
}
