package com.example.doctorhouse.infrastructure.adapter.out.security;

import com.example.doctorhouse.domain.model.User;
import com.example.doctorhouse.domain.port.out.TokenProviderPort;
import com.example.doctorhouse.infrastructure.config.security.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TokenProviderAdapter implements TokenProviderPort {

    private final JwtUtils jwtUtils;
    private final UserDetailsService userDetailsService;

    @Override
    public String generateToken(User user) {
        // Load UserDetails from Security Context (or DB) to match structure expected by
        // JwtUtils
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        return jwtUtils.generateToken(userDetails);
    }
}
