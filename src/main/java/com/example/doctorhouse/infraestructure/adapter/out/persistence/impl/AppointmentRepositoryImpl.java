package com.example.doctorhouse.infraestructure.adapter.out.persistence.impl;

import com.example.doctorhouse.domain.model.AppointmentModel;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.entity.AppointmentEntity;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.mapper.AppointmentMapper;
import com.example.doctorhouse.infraestructure.adapter.out.persistence.repository.AppointmentJpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime; // Importante: Usar LocalTime
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class AppointmentRepositoryImpl implements AppointmentRepositoryPort {

    private final AppointmentJpaRepository jpaRepository;

    // Si el mapper no es estático, inyéctalo aquí también.
    // Asumiré que tus métodos en AppointmentMapper son 'public static'.
    // private final AppointmentMapper appointmentMapper;

    public AppointmentRepositoryImpl(AppointmentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AppointmentModel save(AppointmentModel appointment) {
        AppointmentEntity entityToSave = AppointmentMapper.toEntity(appointment);
        AppointmentEntity savedEntity = jpaRepository.save(entityToSave);
        return AppointmentMapper.toModel(savedEntity);
    }

    @Override
    public List<AppointmentModel> findByDoctorIdAndDate(Long doctorId, LocalDate date) {
        // Como quieres TODAS las citas del día, usamos el rango completo:
        // Desde las 00:00 (MIN) hasta las 23:59:59 (MAX)
        LocalTime startTime = LocalTime.MIN;
        LocalTime endTime = LocalTime.MAX;

        return jpaRepository.findAppointmentsEnRango(
                        doctorId,
                        date,
                        startTime,
                        endTime
                )
                .stream()
                .map(AppointmentMapper::toModel) // Convertimos de Entity a Model
                .collect(Collectors.toList());
    }
}
