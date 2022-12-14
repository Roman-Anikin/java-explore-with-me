CREATE TABLE IF NOT EXISTS endpoint_hit (
    endpoint_hit_id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    app varchar(255),
    uri varchar(255),
    ip varchar(255),
    timestamp TIMESTAMP WITHOUT TIME ZONE,
    CONSTRAINT endpoint_hit_id_pk PRIMARY KEY (endpoint_hit_id)
);