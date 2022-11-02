CREATE TABLE IF NOT EXISTS users (
    user_id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    name varchar(255),
    email varchar(255) UNIQUE,
    CONSTRAINT user_id_pk PRIMARY KEY (user_id)
);

CREATE TABLE IF NOT EXISTS categories (
    category_id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    name varchar(255) UNIQUE,
    CONSTRAINT category_id_pk PRIMARY KEY (category_id)
);

CREATE TABLE IF NOT EXISTS events (
    event_id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    annotation varchar(2000),
    category_id INTEGER NOT NULL REFERENCES categories(category_id) ON UPDATE CASCADE,
    confirmed_requests INTEGER,
    create_date TIMESTAMP WITHOUT TIME ZONE,
    description varchar(7000),
    event_date TIMESTAMP WITHOUT TIME ZONE,
    initiator_id INTEGER NOT NULL REFERENCES users(user_id),
    lat NUMERIC,
    lon NUMERIC,
    paid BOOLEAN,
    participant_limit INTEGER,
    published_date TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    state varchar(255),
    title varchar(255),
    views INTEGER,
    CONSTRAINT event_id_pk PRIMARY KEY (event_id)
);

CREATE TABLE IF NOT EXISTS requests (
    request_id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    event_id INTEGER NOT NULL REFERENCES events(event_id),
    requester_id INTEGER NOT NULL REFERENCES users(user_id),
    created TIMESTAMP WITHOUT TIME ZONE,
    status varchar(255),
    CONSTRAINT request_id_pk PRIMARY KEY (request_id)
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_event_id_requester_id
ON requests(event_id, requester_id);

CREATE TABLE IF NOT EXISTS compilations (
    compilation_id INTEGER NOT NULL GENERATED BY DEFAULT AS IDENTITY,
    pinned BOOLEAN,
    title varchar(255),
    CONSTRAINT compilation_id_pk PRIMARY KEY (compilation_id)
);

CREATE TABLE IF NOT EXISTS event_compilation (
    event_id INTEGER NOT NULL REFERENCES events(event_id) ON UPDATE CASCADE,
    compilation_id INTEGER NOT NULL REFERENCES compilations(compilation_id) ON UPDATE CASCADE,
    CONSTRAINT event_compilation_pk PRIMARY KEY (event_id ,compilation_id)
);
