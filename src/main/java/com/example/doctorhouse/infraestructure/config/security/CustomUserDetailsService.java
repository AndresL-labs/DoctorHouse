package com.example.doctorhouse.infraestructure.config.security;

import com.example.doctorhouse.domain.model.UserModel;
import com.example.doctorhouse.domain.port.out.UserRepositoryPort;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepositoryPort userRepositoryPort;

    public CustomUserDetailsService(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserModel userModel = userRepositoryPort.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        String roleName = userModel.getRole() != null ? userModel.getRole().name() : "PATIENT";
        if (roleName.startsWith("ROLE_")) {
            roleName = roleName.substring(5);
        }

        return User.builder()
                .username(userModel.getEmail()) // Spring usa "userModelname" como campo interno, aquí ponemos email
                .password(userModel.getPassword()) // contraseña hashed
                .roles(roleName) // Spring Security añadirá ROLE_ automáticamente
                .build();
    }
}
