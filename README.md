# 🏥 DoctorHouse --- 

## Épica
Digitalización y Trazabilidad de la Atención Médica Domiciliaria (MVP)

Descripción: Desarrollar una plataforma web centralizada que permita a las IPS gestionar eficientemente el ciclo
completo de una visita médica domiciliaria. Esto abarca desde el registro administrativo de pacientes y médicos,
pasando por la asignación inteligente de citas basada en disponibilidad, hasta el registro clínico digital y la
generación automática de soportes legales de la atención. El objetivo es eliminar el uso de papel y garantizar la
integridad de la información médica.

------------------------------------------------------------------------

# Feature 1: Gestión Administrativa

## HU-01: Registro de usuarios

Como analista,
quiero registrar un usuario con cualquier rol (Paciente, Doctor o Analista),
para que pueda acceder al sistema con permisos adecuados desde el primer día.

### ✔️ Criterios de Aceptación

1. Formulario único de registro
- Existe un solo formulario para crear usuarios de cualquier rol.
- El formulario incluye un dropdown obligatorio para seleccionar: Paciente, Doctor o Analista.
- Los campos obligatorios deben estar claramente marcados.

2. Validaciones

- El sistema valida que el documento sea único en la base de datos.
- Si el documento ya existe, el sistema rechaza el registro con un mensaje claro.
- El correo también debe ser único (opcional, pero recomendado).

3. Contraseña por defecto + Cambio obligatorio
- Al crear el usuario, el sistema genera una contraseña por defecto segura.
- En el primer inicio de sesión, el usuario es redirigido automáticamente a la pantalla de Cambio de Contraseña.
- No puede acceder al sistema sin haber cambiado su contraseña.

4. Persistencia

- El usuario debe almacenarse con el campo: `role = DOCTOR | PATIENT | ANALYST`
- role = DOCTOR | PATIENT | ANALYST

5. Seguridad y Accesos
- Un Paciente no puede acceder a pantallas exclusivas del Doctor (ej. lista de solicitudes, cierre de visita).
- Un Doctor no puede acceder a pantallas administrativas del Analista.
- El sistema valida los permisos mediante middleware/guardia antes de cargar cualquier vista o endpoint.

### 🛠️ Tareas back

- Crear entidad User con campos: id, nombre, documento, correo, teléfono, rol, contraseña, estado, createdAt.
- Implementar enumeración Role { DOCTOR, PATIENT, ANALYST }.
- Crear repositorio para persistencia.
- Agregar validación de unicidad para documento y correo.
- Crear caso de uso RegisterUserUseCase.
- Inyectar puerto de persistencia.
- Implementar generación de contraseña por defecto (segura).
- Implementar política de "primer login → requiere cambio de contraseña".
- Retornar DTO limpio para la capa de aplicación.
- Crear endpoint POST /users/register.
- Validar body con librería (Yup / Joi / Zod / DTO).
- Manejar errores de duplicado.
- Devolver mensaje de confirmación y datos básicos del usuario.
- Implementar middleware/guard para verificar role.
- Configurar rutas y permisos:
/doctor/* → Solo DOCTOR
/patient/* → Solo PATIENT
/admin/* → Solo ANALYST
- Implementar política de primer login → redirect obligatorio a /change-password.

### Tareas front

- Crear formulario único de registro.
- Crear dropdown con los roles.
- Validaciones UI: campos requeridos, documento numérico, correo válido.
- Mostrar errores del backend (documento duplicado).
- Crear vista para Cambio Obligatorio de Contraseña.
- Implementar lógica “bloqueo de navegación hasta cambiar contraseña”.

### Test
- Caso de prueba: registro exitoso.
- Caso de prueba: documento duplicado.
- Caso de prueba: acceso denegado a vistas prohibidas por rol.
- Caso de prueba: primer inicio de sesión → obliga a cambiar la contraseña.

------------------------------------------------------------------------

# Feature 2: Gestión de Agendamiento

## HU-02: Agendamiento de Visita Domiciliaria (Core)

Como paciente, quiero asignar una visita domiciliaria a un médico específico en una fecha y hora, 
para organizar la logística del día y asegurar la atención.

### Criterios de Aceptación
1. Validación de Disponibilidad
- La plataforma no permite agendar si el médico ya tiene una cita dentro del mismo rango horario.
- La duración por defecto de una cita es de 45 minutos.
- Para evitar complejidad, se considera disponibilidad libre si: (nueva_cita_inicio >= cita_existente_fin) OR (nueva_cita_fin <= cita_existente_inicio)
- No se hace cálculo de desplazamiento real; opcionalmente puede ampliarse un “buffer” fijo (ej. +15 min) si el negocio lo requiere.

2. Validación de Estado del Médico

- Si el médico tiene estado INACTIVO, se impide su selección en el formulario.
- Intentos manuales vía API deben recibir error: `400 - El médico seleccionado no está activo.`

3. Creación del Registro
- Al agendarse, la cita debe crearse con estado: `PROGRAMADA`

4. Interfaz Web (Front - Analista / Paciente)
- Debe existir un dropdown con todos los médicos activos disponibles.
- Al seleccionar un médico + un día, el sistema muestra su disponibilidad (citas ocupadas y bloques libres).
- El calendario debe permitir elegir: Día, Hora disponible según cálculo del backend
- La UI debe impedir seleccionar horas que ya estén ocupadas.

5. Reglas simples de duración (sin cálculos complejos)
- Duración fija: 45 min.
- Opción: permitir extender a 60 min dependiendo del tipo de servicio (parametrizable).
- Si el negocio quiere simular desplazamientos, se agrega un buffer fijo configurable (ej. 15 min).

### Tareas back
- Crear entidad Appointment con campos: id, patientId, doctorId, startAt, endAt, duration, status.
- Crear enum AppointmentStatus { PROGRAMADA, CANCELADA, FINALIZADA }.
- Asegurar integridad con llaves foráneas hacia User.
- Crear puerto AppointmentRepository.
- Crear caso de uso ScheduleAppointmentUseCase.
- Consultar citas existentes del médico para la fecha seleccionada.
- Ejecutar validación de disponibilidad por choque de horarios.
- Validar que el médico esté ACTIVO.
- Calcular endAt = startAt + 45 min (o duración configurable).
- Persistir cita con estado PROGRAMADA.
- Crear endpoint POST /appointments/schedule.
- Validar input: médico, paciente, fecha, hora.
- Retornar error claro si no hay disponibilidad.
- Retornar DTO con información de la cita creada.
- Crear endpoint GET /appointments/availability?doctorId=&date=.
- Calcular bloques libres del día:
- Basado en: 8AM–6PM (configurable)
- Duración de la cita (45 min)
- Citas existentes.
- Retornar una lista de horas disponibles.

### Tareas front
- Crear pantalla “Agendar visita”.
- Dropdown con médicos activos.
- Calendario para seleccionar día.
- Al seleccionar día → consumir API de disponibilidad.
- Mostrar lista de bloques horarios disponibles (ej. 9:00, 9:45, 10:30).
- Validación de formularios.
- Enviar la solicitud al endpoint /appointments/schedule.
- Mostrar mensaje de confirmación.
- Manejar errores de disponibilidad.

### Test
- Agendar cita válida → debe crearse con estado PROGRAMADA.
- Intentar agendar cita cuando ya existe una en ese rango → debe rechazarla.
- Intentar agendar cita a médico INACTIVO → error.
- Validar que el frontend solo muestre médicos activos.
- Validar que el frontend no muestre horarios ocupados.

## HU-03: Visualización de Agenda del Médico (“Mi Ruta”)

Como médico domiciliario, quiero ver mi lista de visitas asignadas para el día actual,
para saber a dónde debo dirigirme y organizar mi ruta del día.

### ✔️ Criterios de Aceptación
1. Cada cita debe mostrar:
- Hora de inicio (formato 24h o 12h según definición futura).
- Nombre del paciente.
- Dirección del paciente.
- Condición especial del paciente (si existe).
- Estado de la cita (solo lectura), que será PROGRAMADA o FINALIZADA según aplique.

2. Ordenamiento
- La lista debe estar ordenada por hora de inicio ascendente.
- Si hay dos citas con la misma hora (caso extremo), se ordenan por hora de creación.

3. Filtro automático
- La agenda debe mostrar solo las citas del día actual.
- El médico no debe ver citas de otros médicos.
- (Opcional futuro) Filtrar por fecha manualmente, pero por ahora solo “Hoy”.

4. Interfaz
- Debe ser una interfaz limpia, simple y de lectura rápida.
- Cada cita debe mostrarse como una tarjeta o fila compacta.
- Debe mostrarse un mensaje si no hay citas asignadas para hoy.

5. Seguridad
- Solo usuarios con role = DOCTOR pueden acceder a esta vista.
- Intentos de acceso como Paciente o Analista deben recibir 403 - Acceso no autorizado.

### Tareas Back
- Crear caso de uso GetTodayRouteUseCase.
- Recibir doctorId (desde token autenticado).
- Consultar citas del día actual (startAt entre 00:00 y 23:59).
- Incluir datos del paciente mediante join.
- Ordenar por hora de inicio ascendente.
- Retornar DTO limpio para frontend.
- Crear endpoint GET /appointments/my-route/today.
- Proteger con middleware de autenticación.
- Validar que el rol sea DOCTOR.
- Retornar lista de cita(s) con paciente y dirección.

### Tareas front
- Crear página /doctor/my-route.
- Llamar al endpoint GET /appointments/my-route/today.
- Mostrar tarjetas ordenadas por hora.
- Componente "TarjetaCita" con: Hora, Nombre paciente, Dirección, Condición especial
- Estado vacío: “No tienes visitas programadas para hoy”.
- Diseño minimalista tipo lista vertical.
- Resaltar la primera cita como "Próxima visita" (opcional).
- Mostrar loading state mientras carga.

### Test
- Verifica que un médico con citas vea su lista ordenada.
- Verifica que un médico sin citas vea mensaje vacío.
- Verifica que un Paciente o Analista no pueda acceder (403).
- Verifica que la fecha usada sea siempre el día actual, independientemente de la zona horaria config.
- Verifica visualmente que los datos obligatorios estén presentes.

------------------------------------------------------------------------

# Feature 3: Atención médica
## HU-04: Registro de Evolución Clínica (Atención)
Como médico domiciliario, quiero registrar hallazgos médicos, signos vitales y la prescripción en una cita asignada,
para dejar constancia legal y clínica de la atención prestada.

### ✔️ Criterios de Aceptación
1. Restricciones
- Solo se puede registrar información clínica en citas cuyo estado sea:
PROGRAMADA
EN_PROCESO
- Si se intenta registrar evolución en una cita FINALIZADA o CANCELADA →
Debe devolver error 400: "La cita no permite registrar evolución clínica."

2. Al guardar la evolución 
- La cita cambia automáticamente a: FINALIZADA
- Se registra completedAt (fecha/hora de cierre de la atención).

3. La evolución debe incluir campos requeridos:
- Tensión arterial (ej. 120/80)
- Frecuencia cardíaca (latidos por minuto)
- Diagnóstico (texto libre)
- Observaciones (texto libre)
- Opcional futuro: peso, saturación O₂, temperatura, lista de medicamentos, firma digital.

4. Seguridad y permisos
- Solo usuarios con rol DOCTOR pueden registrar evolución.
- Un médico solo puede registrar evolución en sus propias citas.

5. Persistencia

Debe persistirse toda la información en una entidad separada de la cita:

AppointmentEvolution
- evolutionId
- appointmentId
- doctorId
- bloodPressure
- heartRate
- diagnosis
- observations
- createdAt

6. Registro Clínico

Cada evolución debe generar un registro 100% trazable:

- Fecha
- Profesional
- Diagnóstico
- Datos vitales
- Observaciones

No debe permitir eliminar evoluciones (solo añadir, nunca borrar).

### Tareas back
- Crear entidad AppointmentEvolution
- Crear repositorio y puerto EvolutionRepository
- Agregar campo completedAt a Appointment
- Asegurar relación 1:1 entre cita y evolución (una cita → una evolución)
- Crear RegisterClinicalEvolutionUseCase
- Validar: Que la cita existe
- Validar: Que pertenece al médico autenticado
- Validar: Que el estado es PROGRAMADA o EN_PROCESO
- Guardar evolución en repositorio
- Actualizar estado de cita a FINALIZADA
- Registrar timestamp completedAt
- Crear endpoint POST /appointments/{id}/evolution
- Validar body (campos requeridos)
- Proteger endpoint con rol DOCTOR
- Manejar errores: Cita no pertenece al médico, Cita no permite evolución, Body incompleto
- Devolver DTO con evolución guardada + cita actualizada

### Tareas front
- Pantalla /doctor/appointment/:id/atencion
- Mostrar datos del paciente y una breve cabecera (hora, dirección)
- Formulario con: Tensión arterial, Frecuencia cardíaca, Diagnóstico, Observaciones, Botón “Guardar y Finalizar”
- Validación UI: campos obligatorios
- POST al endpoint
- Redirigir a “Mi Ruta” o a un mensaje: “Atención finalizada exitosamente”

### Test
- Registrar evolución correcta → cita pasa a FINALIZADA
- Intentar registrar evolución en cita finalizada → error
- Intentar registrar evolución en cita de otro médico → error
- Todos los campos requeridos obligatorios
- Guardado exitoso crea registro en base de datos

## HU-05: Generación de Resumen de Atención (PDF)
Como médico o paciente, quiero descargar un PDF con el resumen de la visita al finalizar la atención,
para tener un soporte físico o digital de la consulta realizada.

✔️ Criterios de Aceptación
1. Disparador automático. Cuando la cita cambia a estado FINALIZADA
el sistema debe generar automáticamente un PDF basado en una plantilla simple.

2. Plantilla del PDF. El PDF debe contener como mínimo:
- Logo de la empresa (cabecera).
- Datos del paciente: Nombre completo, Documento, Dirección, 
- Datos del médico: Nombre, Registro profesional (si aplica)
- Datos de la cita: Fecha y hora de la atención, Estado FINALIZADA, 
- Resumen clínico: Signos vitales registrados, Diagnóstico
- Observaciones
- Pie de página con información legal mínima.
- El diseño debe ser simple, legible y de una sola página siempre que sea posible.

3. Descarga manual
- El médico y el paciente deben tener la opción de descargar el PDF desde su panel.
- No se requiere envío por correo en esta HU (puede ser otra HU si se quiere).

4. Persistencia del archivo
El sistema debe almacenar el PDF generado, guardando:

- appointmentId
- pdfUrl (ruta o bucket)
- generatedAt

5. Seguridad
Solo pueden acceder al PDF:
- El médico asignado
- El paciente de la cita
- Analistas/autorizados (rol administrativo)

6. Auditoría
Cada generación de PDF debe generar un registro de auditoría:

- “PDF generado para cita X por evento de FINALIZACIÓN”

### Tareas back
- Crear GenerateAppointmentSummaryPDFUseCase.
- Recibir appointmentId.
- Obtener evolución clínica asociada.
- Obtener paciente + médico.
- Ensamblar datos para el PDF.
- Llamar a servicio generador de PDF.
- Guardar archivo en almacenamiento (local/S3/bucket).
- Persistir registro AppointmentPDF.
- En el caso de uso RegisterClinicalEvolutionUseCase, cuando la cita pase a FINALIZADA:
- Disparar evento AppointmentFinalizedEvent.
- Listener del evento ejecuta GenerateAppointmentSummaryPDFUseCase.
- (Event-driven: compatible con arquitectura hexagonal)
- Endpoint GET /appointments/{id}/pdf.
- Validar permisos: médico, paciente o analista.
- Retornar el PDF directamente o el enlace temporal.

### Tareas Front
- Agregar botón “Descargar Resumen en PDF” en Vista del Médico → historial / detalle de cita
- Vista del Paciente → historial / mis citas
- Al hacer clic → llamar a GET /appointments/{id}/pdf.
- Manejar estados: “Generando PDF…” (si no existe aún), “PDF disponible”
- Mostrar icono de archivo
- Mostrar fecha de generación del PDF

### Test

- Verificar que al finalizar cita se genere automáticamente el PDF.
- Verificar que el contenido del PDF tenga todos los campos obligatorios.
- Verificar que solo médico/paciente/analista puedan descargarlo.
- Verificar auditoría de generación.
- Verificar que una cita sin evolución → no debe generar PDF.