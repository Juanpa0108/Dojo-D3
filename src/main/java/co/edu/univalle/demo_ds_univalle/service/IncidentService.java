package co.edu.univalle.demo_ds_univalle.service;

import co.edu.univalle.demo_ds_univalle.exception.BusinessException;
import co.edu.univalle.demo_ds_univalle.exception.ResourceNotFoundException;
import co.edu.univalle.demo_ds_univalle.model.IncidentModel;
import co.edu.univalle.demo_ds_univalle.repository.IncidentRepository;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Servicio que contiene la lógica de negocio para la gestión de incidentes. */
@Service
public class IncidentService {

    /** Estado inicial de todo incidente reportado. */
    private static final String STATUS_REPORTED = "REPORTED";

    /** Valores de severidad permitidos (coinciden con el CHECK de la tabla). */
    private static final Set<String> SEVERITIES = Set.of("MINOR", "SEVERE");

    /** Repositorio de acceso a datos de incidentes. */
    private final IncidentRepository incidentRepository;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param incidentRepository repositorio de incidentes
     */
    public IncidentService(final IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    /**
     * Registra un nuevo incidente. Genera el id, fija el estado inicial REPORTED
     * y asigna la fecha de reporte si no viene informada.
     *
     * @param incident datos del incidente a registrar
     * @return el incidente creado
     * @throws BusinessException si la severidad no es válida
     */
    @Transactional
    public IncidentModel crear(final IncidentModel incident) {
        validarSeveridad(incident.getSeverity());
        incident.setId(UUID.randomUUID());
        incident.setStatus(STATUS_REPORTED);
        if (incident.getReportedAt() == null) {
            incident.setReportedAt(OffsetDateTime.now());
        }
        return incidentRepository.save(incident);
    }

    /**
     * Retorna todos los incidentes registrados.
     *
     * @return lista de incidentes
     */
    @Transactional(readOnly = true)
    public List<IncidentModel> obtenerTodos() {
        return incidentRepository.findAll();
    }

    /**
     * Busca un incidente por su id.
     *
     * @param id identificador del incidente
     * @return el incidente encontrado
     * @throws ResourceNotFoundException si el incidente no existe
     */
    @Transactional(readOnly = true)
    public IncidentModel obtenerPorId(final UUID id) {
        return incidentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Incidente con id " + id + " no encontrado"
                ));
    }

    /**
     * Busca los incidentes de un vehículo.
     *
     * @param vehicleId identificador del vehículo
     * @return lista de incidentes del vehículo
     */
    @Transactional(readOnly = true)
    public List<IncidentModel> buscarPorVehiculo(final UUID vehicleId) {
        return incidentRepository.findAllByVehicleId(vehicleId);
    }

    /**
     * Busca los incidentes asociados a una asignación.
     *
     * @param assignmentId identificador de la asignación
     * @return lista de incidentes de la asignación
     */
    @Transactional(readOnly = true)
    public List<IncidentModel> buscarPorAsignacion(final UUID assignmentId) {
        return incidentRepository.findAllByAssignmentId(assignmentId);
    }

    /**
     * Busca los incidentes por severidad.
     *
     * @param severity severidad a buscar (MINOR o SEVERE)
     * @return lista de incidentes con esa severidad
     * @throws BusinessException si la severidad no es válida
     */
    @Transactional(readOnly = true)
    public List<IncidentModel> buscarPorSeveridad(final String severity) {
        validarSeveridad(severity);
        return incidentRepository.findAllBySeverity(severity);
    }

    /**
     * Busca los incidentes reportados en un rango de fechas.
     *
     * @param from fecha y hora inicial (inclusive)
     * @param to fecha y hora final (inclusive)
     * @return lista de incidentes del rango, del más reciente al más antiguo
     * @throws BusinessException si la fecha inicial es posterior a la final
     */
    @Transactional(readOnly = true)
    public List<IncidentModel> buscarPorRangoFechas(
            final OffsetDateTime from, final OffsetDateTime to) {
        if (from.isAfter(to)) {
            throw new BusinessException(
                    "La fecha inicial no puede ser posterior a la fecha final"
            );
        }
        return incidentRepository
                .findAllByReportedAtBetweenOrderByReportedAtDesc(from, to);
    }

    /**
     * Valida que la severidad sea una de las permitidas.
     *
     * @param severity severidad a validar
     * @throws BusinessException si la severidad es nula o no está permitida
     */
    private void validarSeveridad(final String severity) {
        if (severity == null || !SEVERITIES.contains(severity)) {
            throw new BusinessException(
                    "Severidad inválida: " + severity + ". Valores permitidos: MINOR, SEVERE"
            );
        }
    }
}