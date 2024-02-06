ALTER TABLE transactions
DROP COLUMN limit_exceeded;


ALTER TABLE transactions
ADD COLUMN limit_exceeded_id BIGINT;

ALTER TABLE transactions
ADD CONSTRAINT fk_limit_exceeded_id
FOREIGN KEY (limit_exceeded_id) REFERENCES limits(id);
