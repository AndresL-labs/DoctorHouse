CREATE TABLE IF NOT EXISTS appointments (
    id_appointment BIGSERIAL PRIMARY KEY,
    patient_id BIGINT NOT NULL,
    doctor_id BIGINT NOT NULL,
    appointment_date_time TIMESTAMP NOT NULL,
    start_at TIME NOT NULL,
    end_at TIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Keys (asumiendo tabla users existe)
    CONSTRAINT fk_appointment_patient
        FOREIGN KEY (patient_id)
        REFERENCES users(id)
        ON DELETE RESTRICT,

    CONSTRAINT fk_appointment_doctor
        FOREIGN KEY (doctor_id)
        REFERENCES users(id)
        ON DELETE RESTRICT,

    -- Check constraints
    CONSTRAINT chk_appointment_status
        CHECK (status IN ('SCHEDULED', 'CANCELED', 'COMPLETED')),

    CONSTRAINT chk_appointment_times
        CHECK (end_at > start_at)
);

-- Índices para optimización
CREATE INDEX idx_appointment_doctor_date
    ON appointments(doctor_id, appointment_date_time);

CREATE INDEX idx_appointment_patient
    ON appointments(patient_id);

CREATE INDEX idx_appointment_status
    ON appointments(status);

-- Índice compuesto para búsqueda de conflictos
CREATE INDEX idx_appointment_doctor_time_status
    ON appointments(doctor_id, appointment_date_time, start_at, end_at, status);

-- Comentarios
COMMENT ON TABLE appointments IS 'Citas médicas domiciliarias';
COMMENT ON COLUMN appointments.appointment_date_time IS 'Fecha y hora base de la cita';
COMMENT ON COLUMN appointments.start_at IS 'Hora de inicio (TIME)';
COMMENT ON COLUMN appointments.end_at IS 'Hora de fin (TIME)';
COMMENT ON COLUMN appointments.status IS 'Estados: SCHEDULED, CANCELED, COMPLETED';