CREATE TABLE IF NOT EXISTS appointment_evolutions (
    id BIGSERIAL PRIMARY KEY,
    appointment_id BIGINT NOT NULL UNIQUE,
    blood_pressure VARCHAR(20) NOT NULL,
    heart_rate INTEGER NOT NULL,
    temperature DECIMAL(4,1),
    oxygen_saturation INTEGER,
    weight_kg DECIMAL(5,2),
    diagnosis TEXT NOT NULL,
    observations TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Foreign Key
    CONSTRAINT fk_evolution_appointment
        FOREIGN KEY (appointment_id)
        REFERENCES appointments(id_appointment)
        ON DELETE RESTRICT,

    -- Check constraints
    CONSTRAINT chk_heart_rate
        CHECK (heart_rate > 0),
    
    CONSTRAINT chk_temperature
        CHECK (temperature IS NULL OR (temperature >= 30 AND temperature <= 45)),
    
    CONSTRAINT chk_oxygen_saturation
        CHECK (oxygen_saturation IS NULL OR (oxygen_saturation >= 0 AND oxygen_saturation <= 100)),
    
    CONSTRAINT chk_weight_kg
        CHECK (weight_kg IS NULL OR weight_kg > 0)
);

-- Índices para optimización
CREATE INDEX idx_evolution_appointment_id
    ON appointment_evolutions(appointment_id);

-- Comentarios
COMMENT ON TABLE appointment_evolutions IS 'Evoluciones clínicas de las citas médicas';
COMMENT ON COLUMN appointment_evolutions.appointment_id IS 'Referencia única a la cita (relación 1:1)';
COMMENT ON COLUMN appointment_evolutions.blood_pressure IS 'Tensión arterial (ej. 120/80)';
COMMENT ON COLUMN appointment_evolutions.heart_rate IS 'Frecuencia cardíaca en latidos por minuto';
COMMENT ON COLUMN appointment_evolutions.diagnosis IS 'Diagnóstico médico';

