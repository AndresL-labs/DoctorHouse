package com.example.doctorhouse.infraestructure.adapter.out.persistence.impl;

import com.example.doctorhouse.domain.port.out.DoctorRepositoryPort;
import org.springframework.stereotype.Repository;

@Repository
public class DoctorRepositoryImpl implements DoctorRepositoryPort {

    // TODO: Inyectar el JpaRepository de Doctor cuando exista.
    // private final DoctorJpaRepository doctorJpaRepository;

    // public DoctorRepositoryImpl(DoctorJpaRepository doctorJpaRepository) {
    //     this.doctorJpaRepository = doctorJpaRepository;
    // }

    @Override
    public boolean isDoctorActive(Long doctorId) {
        // Lógica temporal: siempre devuelve true.
        // TODO: Implementar la lógica real para verificar si el doctor está activo en la base de datos.
        // Por ejemplo:
        // return doctorJpaRepository.findById(doctorId)
        //         .map(DoctorEntity::isActive)
        //         .orElse(false);
        return true;
    }
}
