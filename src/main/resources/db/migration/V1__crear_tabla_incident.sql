CREATE TABLE incident (
    inc_id                  UUID          NOT NULL,
    inc_assignment_id       UUID          NOT NULL,
    inc_vehicle_id          UUID          NOT NULL,
    inc_driver_id           UUID          NOT NULL,
    inc_severity            VARCHAR(10)   NOT NULL,
    inc_description         VARCHAR(500)  NOT NULL,
    inc_requires_assistance BOOLEAN       NOT NULL,
    inc_status              VARCHAR(20)   NOT NULL DEFAULT 'REPORTED',
    inc_reported_at         TIMESTAMPTZ   NOT NULL,
    inc_created_at          TIMESTAMPTZ   NOT NULL DEFAULT NOW(),
    CONSTRAINT pk_incident PRIMARY KEY (inc_id),
    CONSTRAINT ck_incident_severity CHECK (inc_severity IN ('MINOR', 'SEVERE')),
    CONSTRAINT ck_incident_status   CHECK (inc_status IN ('REPORTED'))
);

-- Índices para los filtros de GET /incidents (INC-06)
CREATE INDEX ix_incident_vehicle     ON incident (inc_vehicle_id);
CREATE INDEX ix_incident_assignment  ON incident (inc_assignment_id);
CREATE INDEX ix_incident_severity    ON incident (inc_severity);
CREATE INDEX ix_incident_reported_at ON incident (inc_reported_at DESC);