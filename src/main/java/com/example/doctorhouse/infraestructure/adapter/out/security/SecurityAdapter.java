package com.example.doctorhouse.infraestructure.adapter.out.security;

import com.example.doctorhouse.domain.port.out.SecurityPort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SecurityAdapter implements SecurityPort {

    @Override
    public Optional<Long> getAuthenticatedUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return Optional.empty();
        }

        // Intentamos obtener el ID del principal (asumiendo que es el username o parte
        // del principal)
        try {
            // Ajustar según la implementación real de UserDetails.
            // Si el username es el ID:
            return Optional.of(Long.parseLong(authentication.getName()));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    @Override
    public String getAuthenticatedUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            return "";
        }
        return authentication.getAuthorities().stream()
                .findFirst()
                .map(Object::toString)
                .orElse("");
    }
}
