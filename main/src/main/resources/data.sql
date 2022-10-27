DELETE FROM requests;
ALTER TABLE requests ALTER COLUMN request_id RESTART WITH 1;

DELETE FROM event_compilation;

DELETE FROM events;
ALTER TABLE events ALTER COLUMN event_id RESTART WITH 1;

DELETE FROM users;
ALTER TABLE users ALTER COLUMN user_id RESTART WITH 1;

DELETE FROM categories;
ALTER TABLE categories ALTER COLUMN category_id RESTART WITH 1;

DELETE FROM compilations;
ALTER TABLE compilations ALTER COLUMN compilation_id RESTART WITH 1;


