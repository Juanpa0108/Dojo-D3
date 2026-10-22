package co.edu.univalle.demo_ds_univalle.controller;

import co.edu.univalle.demo_ds_univalle.exception.BusinessException;
import co.edu.univalle.demo_ds_univalle.model.IncidentModel;
import co.edu.univalle.demo_ds_univalle.service.IncidentService;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/** Controlador REST para la gestión de incidentes. */
@RestController
@RequestMapping("/incidents")
public class IncidentController {

    /** Servicio con la lógica de negocio de incidentes. */
    private final IncidentService incidentService;

    /**
     * Constructor con inyección de dependencias.
     *
     * @param incidentService servicio de incidentes
     */
    public IncidentController(final IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * Registra un nuevo incidente.
     *
     * @param incident datos del incidente a registrar
     * @return 201 Created con el incidente creado y su ubicación en el header Location
     */
    @PostMapping
    public ResponseEntity<IncidentModel> crear(@RequestBody final IncidentModel incident) {
        IncidentModel creado = incidentService.crear(incident);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(creado.getId())
                .toUri();
        // Se consulta de nuevo para devolver también inc_created_at, que llena la base de datos
        return ResponseEntity.created(location)
                .body(incidentService.obtenerPorId(creado.getId()));
    }

    /**
     * Lista incidentes. Sin parámetros devuelve todos; con parámetros aplica un filtro.
     * Prioridad de filtros: vehicleId, assignmentId, severity, rango from/to.
     *
     * @param vehicleId filtra por vehículo (opcional)
     * @param assignmentId filtra por asignación (opcional)
     * @param severity filtra por severidad MINOR o SEVERE (opcional)
     * @param from inicio del rango de fecha de reporte, formato ISO-8601 (opcional)
     * @param to fin del rango de fecha de reporte, formato ISO-8601 (opcional)
     * @return lista de incidentes que cumplen el filtro
     */
    @GetMapping
    public ResponseEntity<List<IncidentModel>> listar(
            @RequestParam(required = false) final UUID vehicleId,
            @RequestParam(required = false) final UUID assignmentId,
            @RequestParam(required = false) final String severity,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final OffsetDateTime from,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) final OffsetDateTime to) {
        if (vehicleId != null) {
            return ResponseEntity.ok(incidentService.buscarPorVehiculo(vehicleId));
        }
        if (assignmentId != null) {
            return ResponseEntity.ok(incidentService.buscarPorAsignacion(assignmentId));
        }
        if (severity != null) {
            return ResponseEntity.ok(incidentService.buscarPorSeveridad(severity));
        }
        if (from != null || to != null) {
            if (from == null || to == null) {
                throw new BusinessException("Debe indicar tanto 'from' como 'to' para filtrar por fecha");
            }
            return ResponseEntity.ok(incidentService.buscarPorRangoFechas(from, to));
        }
        return ResponseEntity.ok(incidentService.obtenerTodos());
    }

    /**
     * Obtiene un incidente por su id.
     *
     * @param id identificador del incidente
     * @return el incidente encontrado
     */
    @GetMapping("/{id}")
    public ResponseEntity<IncidentModel> obtenerPorId(@PathVariable final UUID id) {
        return ResponseEntity.ok(incidentService.obtenerPorId(id));
    }
}