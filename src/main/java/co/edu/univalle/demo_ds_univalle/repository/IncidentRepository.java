package co.edu.univalle.demo_ds_univalle.repository;

import co.edu.univalle.demo_ds_univalle.model.IncidentModel;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repositorio JPA para la entidad IncidentModel.
 * Spring Data genera automáticamente la implementación en tiempo de ejecución.
 */
@Repository
public interface IncidentRepository extends JpaRepository<IncidentModel, UUID> {

    /**
     * Busca los incidentes de un vehículo.
     *
     * @param vehicleId identificador del vehículo
     * @return lista de incidentes del vehículo
     */
    List<IncidentModel> findAllByVehicleId(UUID vehicleId);

    /**
     * Busca los incidentes asociados a una asignación.
     *
     * @param assignmentId identificador de la asignación
     * @return lista de incidentes de la asignación
     */
    List<IncidentModel> findAllByAssignmentId(UUID assignmentId);

    /**
     * Busca los incidentes por severidad.
     *
     * @param severity severidad a buscar (MINOR o SEVERE)
     * @return lista de incidentes con esa severidad
     */
    List<IncidentModel> findAllBySeverity(String severity);

    /**
     * Busca los incidentes reportados dentro de un rango de fechas,
     * ordenados del más reciente al más antiguo.
     *
     * @param from fecha y hora inicial (inclusive)
     * @param to fecha y hora final (inclusive)
     * @return lista de incidentes reportados en el rango
     */
    List<IncidentModel> findAllByReportedAtBetweenOrderByReportedAtDesc(
            OffsetDateTime from, OffsetDateTime to);
}