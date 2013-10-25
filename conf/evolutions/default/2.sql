
# --- !Ups

ALTER TABLE registration ADD COLUMN (teammate2 CHAR(7));

# --- !Downs

ALTER TABLE registration DROP COLUMN teammate2;