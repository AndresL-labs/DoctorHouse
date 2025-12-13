package com.example.doctorhouse.infraestructure.adapter.out.persistence.entity;

import com.example.doctorhouse.domain.model.Role;
import jakarta.persistence.*;
import lombok.Data;

import java.time.OffsetDateTime;

@Data
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String dni;

    @Column
    private String email;

    private String password;
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean isActive;

    private OffsetDateTime createdAt;
}
