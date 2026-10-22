package co.edu.univalle.demo_ds_univalle.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Entidad JPA que representa un incidente reportado durante una asignación de vehículo. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "incident")
public class IncidentModel {

    /** Identificador único del incidente (UUID, se asigna desde la aplicación). */
    @Id
    @Column(name = "inc_id", nullable = false)
    private UUID id;

    /** Identificador de la asignación asociada al incidente. */
    @Column(name = "inc_assignment_id", nullable = false)
    private UUID assignmentId;

    /** Identificador del vehículo involucrado. */
    @Column(name = "inc_vehicle_id", nullable = false)
    private UUID vehicleId;

    /** Identificador del conductor que reporta el incidente. */
    @Column(name = "inc_driver_id", nullable = false)
    private UUID driverId;

    /** Severidad del incidente: MINOR o SEVERE. */
    @Column(name = "inc_severity", nullable = false, length = 10)
    private String severity;

    /** Descripción del incidente. */
    @Column(name = "inc_description", nullable = false, length = 500)
    private String description;

    /** Indica si el incidente requiere asistencia. */
    @Column(name = "inc_requires_assistance", nullable = false)
    private Boolean requiresAssistance;

    /** Estado del incidente (por ahora solo REPORTED). */
    @Column(name = "inc_status", nullable = false, length = 20)
    private String status;

    /** Fecha y hora en que se reportó el incidente. */
    @Column(name = "inc_reported_at", nullable = false)
    private OffsetDateTime reportedAt;

    /** Fecha y hora de creación del registro (la asigna la base de datos). */
    @Column(name = "inc_created_at", nullable = false, insertable = false, updatable = false)
    private OffsetDateTime createdAt;
}