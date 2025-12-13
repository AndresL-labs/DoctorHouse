package com.example.doctorhouse.infraestructure.adapter.in.rest;

import com.example.doctorhouse.domain.port.in.GetTodayRouteUseCase;
import com.example.doctorhouse.infraestructure.adapter.in.mapper.AppointmentRouteRestMapper;
import com.example.doctorhouse.infraestructure.adapter.in.dto.AppointmentRouteResponseDTO;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentRouteController {

    private final GetTodayRouteUseCase getTodayRouteUseCase;

    public AppointmentRouteController(GetTodayRouteUseCase getTodayRouteUseCase) {
        this.getTodayRouteUseCase = getTodayRouteUseCase;
    }

    @GetMapping("/my-route/today")
    public List<AppointmentRouteResponseDTO> getTodayRoute(
            @RequestParam Integer doctorId
    ) {
        return getTodayRouteUseCase.execute(doctorId)
                .stream()
                .map(AppointmentRouteRestMapper::toDto)
                .toList();
    }
}
