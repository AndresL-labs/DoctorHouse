package com.example.doctorhouse.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "user_id") // Maps to 'doctors.user_id' which references 'users.id'
@Data
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class DoctorEntity extends UserEntity {

    @Column(nullable = false, length = 100)
    private String specialty;

    @Column(name = "license_number", nullable = false, unique = true, length = 50)
    private String licenseNumber;

    // Note: 'active' field is present in 'doctors' table in the schema provided by
    // user (active BOOLEAN DEFAULT TRUE)
    // AND 'is_active' is in 'users' table.
    // The DoctorEntity extends UserEntity which has 'active'.
    // If we map the child field, it should be distinct or we should rely on the
    // parent one.
    // The Schema has:
    // users: is_active
    // doctors: active
    // This looks like redundancy or separate status (e.g. User active vs Doctor
    // active availability).
    // I will map it as a separate field 'doctorActive'.

    @Column(name = "active")
    private Boolean doctorActive = true;
}