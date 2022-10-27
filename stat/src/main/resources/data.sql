DELETE FROM endpoint_hit;
ALTER TABLE endpoint_hit ALTER COLUMN endpoint_hit_id RESTART WITH 1;