package com.example.doctorhouse.infraestructure.adapter.in.rest;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;
import com.example.doctorhouse.domain.port.in.GetTodayRouteUseCase;
import com.example.doctorhouse.infraestructure.adapter.in.dto.AppointmentRouteResponseDTO;
import com.example.doctorhouse.infraestructure.adapter.in.mapper.AppointmentRouteRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Controller para la HU-03:
 * GET /appointments/my-route/today
 *
 * Requiere que el SecurityContext contenga claims "userId" y "role".
 * Ajusta la extracción del userId según tu implementación de seguridad.
 */
@RestController
@RequestMapping("/appointments")
public class AppointmentRouteController {

    private final GetTodayRouteUseCase getTodayRouteUseCase;

    public AppointmentRouteController(GetTodayRouteUseCase getTodayRouteUseCase) {
        this.getTodayRouteUseCase = getTodayRouteUseCase;
    }

    @GetMapping("/my-route/today")
    public ResponseEntity<?> getTodayRoute(Authentication authentication) {
        // Verificación básica de rol (puedes reemplazar por @PreAuthorize si configuras Spring Security)
        if (authentication == null || authentication.getAuthorities().stream().noneMatch(a -> a.getAuthority().equals("ROLE_DOCTOR"))) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Acceso no autorizado: solo DOCTOR");
        }

        // Extraer doctorId desde los claims/principal.
        // Ajusta según cómo tu proyecto coloca el claim (Jwt principal, custom UserDetails, etc.)
        Object principal = authentication.getPrincipal();
        UUID doctorId = null;

        try {
            // Ejemplo: si principal tiene método getClaim("userId") (Jwt), o si el principal es UserDetails con username = UUID
            // Aquí asumimos que authentication.getName() devuelve el userId (si no, adapta según tu security).
            doctorId = UUID.fromString(authentication.getName());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo extraer doctorId del token");
        }

        List<AppointmentRouteModel> models = getTodayRouteUseCase.getTodayRoute(doctorId);

        List<AppointmentRouteResponseDTO> response = models.stream()
                .map(AppointmentRouteRestMapper::toDto)
                .collect(Collectors.toList());

        if (response.isEmpty()) {
            return ResponseEntity.ok().body(response); // frontend mostrará "No tienes visitas..."
        } else {
            return ResponseEntity.ok(response);
        }
    }
}
