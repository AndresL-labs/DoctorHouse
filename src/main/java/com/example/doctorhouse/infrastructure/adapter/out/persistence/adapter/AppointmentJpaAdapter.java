package com.example.doctorhouse.infrastructure.adapter.out.persistence.adapter;

import com.example.doctorhouse.domain.model.Appointment;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.AppointmentEntity;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.DoctorEntity;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.entity.PatientEntity;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.mapper.AppointmentPersistenceMapper;
import com.example.doctorhouse.infrastructure.adapter.out.persistence.repository.AppointmentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AppointmentJpaAdapter implements AppointmentRepositoryPort {

    private final AppointmentJpaRepository jpaRepository;
    private final AppointmentPersistenceMapper mapper;

    @Override
    public Appointment save(Appointment appointment) {
        AppointmentEntity entity = mapper.toEntity(appointment);

        PatientEntity patientRef = new PatientEntity();
        patientRef.setId(appointment.getPatientId());
        entity.setPatient(patientRef);

        DoctorEntity doctorRef = new DoctorEntity();
        doctorRef.setId(appointment.getDoctorId());
        entity.setDoctor(doctorRef);

        return mapper.toDomain(jpaRepository.save(entity));
    }

    @Override
    public java.util.List<com.example.doctorhouse.domain.model.AppointmentWithPatient> findByDoctorEmailAndDateRange(
            String email, java.time.LocalDateTime start, java.time.LocalDateTime end) {
        return jpaRepository.findByDoctor_EmailAndScheduledAtBetweenOrderByScheduledAtAsc(email, start, end)
                .stream()
                .map(mapper::toAppointmentWithPatient)
                .toList();
    }

    @Override
    public java.util.List<com.example.doctorhouse.domain.model.AppointmentWithDoctor> findByPatientEmail(String email) {
        return jpaRepository.findByPatient_EmailOrderByScheduledAtDesc(email)
                .stream()
                .map(mapper::toAppointmentWithDoctor)
                .toList();
    }

    @Override
    public java.util.Optional<Appointment> findById(Long id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public java.util.List<Appointment> findByDoctorIdAndDate(Long doctorId, java.time.LocalDateTime start,
            java.time.LocalDateTime end) {
        return jpaRepository.findByDoctor_IdAndScheduledAtBetweenOrderByScheduledAtAsc(doctorId, start, end)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }

    @Override
    public java.util.List<Appointment> findAllScheduledOrderedByDate() {
        return jpaRepository
                .findByStatusOrderByScheduledAtAsc(
                        com.example.doctorhouse.domain.model.enums.AppointmentStatus.PROGRAMADA)
                .stream()
                .map(mapper::toDomain)
                .toList();
    }
}
