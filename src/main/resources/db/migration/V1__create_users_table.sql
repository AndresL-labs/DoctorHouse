CREATE TABLE IF NOT EXISTS users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    dni VARCHAR(20) UNIQUE,
    email VARCHAR(255),
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(20),
    role VARCHAR(20) NOT NULL,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,

    -- Check constraints
    CONSTRAINT chk_user_role
        CHECK (role IN ('DOCTOR', 'PATIENT', 'ANALYST'))
);

-- Índices para optimización
CREATE INDEX idx_users_dni ON users(dni);
CREATE INDEX idx_users_email ON users(email);
CREATE INDEX idx_users_role ON users(role);

-- Comentarios
COMMENT ON TABLE users IS 'Usuarios del sistema (doctores, pacientes, analistas)';
COMMENT ON COLUMN users.dni IS 'Documento de identidad único';
COMMENT ON COLUMN users.role IS 'Rol del usuario: DOCTOR, PATIENT, o ANALYST';
COMMENT ON COLUMN users.is_active IS 'Indica si el usuario está activo en el sistema';

