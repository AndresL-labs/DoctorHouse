package com.example.doctorhouse.infraestructure.adapter.out.persistence.adapter;

import com.example.doctorhouse.domain.model.AppointmentRouteModel;
import com.example.doctorhouse.domain.port.out.AppointmentRepositoryPort;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AppointmentPersistenceAdapter implements AppointmentRepositoryPort {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<AppointmentRouteModel> findTodayRouteByDoctor(
            Integer doctorId,
            LocalDate today
    ) {

        String jpql = """
            SELECT new com.example.doctorhouse.domain.model.AppointmentRouteModel(
                a.id,
                a.startTime,
                CONCAT(u.firstName, ' ', u.lastName),
                p.address,
                p.allergies,
                a.status,
                a.createdAt
            )
            FROM AppointmentEntity a
            JOIN PatientEntity p ON p.userId = a.patientId
            JOIN UserEntity u ON u.id = p.userId
            WHERE a.doctorId = :doctorId
              AND a.appointmentDate = :today
            ORDER BY a.startTime ASC, a.createdAt ASC
        """;

        return entityManager.createQuery(jpql, AppointmentRouteModel.class)
                .setParameter("doctorId", doctorId)
                .setParameter("today", today)
                .getResultList();
    }
}
