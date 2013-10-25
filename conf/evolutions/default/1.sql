
# --- !Ups

CREATE TABLE rdb_group(
    nr INT NOT NULL PRIMARY KEY, 
    regular_date VARCHAR(255) NOT NULL, 
    hiwi_name VARCHAR(255)
);

INSERT INTO rdb_group (nr, regular_date, hiwi_name) VALUES
    (1, 'Monday 8:00 in PK 2.2', NULL),
    (2, 'Monday 8:00 in PK 2.1', NULL),
    (3, 'Tuesday 9:45 in BW 9000', NULL),
    (4, 'Wednesday 9:45 in Phils office', NULL);


CREATE TABLE registration(
    matriculationnumber CHAR(7) NOT NULL PRIMARY KEY, 
    firstname VARCHAR(255) NOT NULL, 
    lastname VARCHAR(255) NOT NULL, 
    email VARCHAR(255) NOT NULL, 
    pick1 INT NOT NULL, 
    pick2 INT NOT NULL, 
    pick3 INT NOT NULL, 
    teammate CHAR(7), 
    attend_to_rdb CHAR(1) NOT NULL CHECK (attend_to_rdb IN ('t', 'f')), 
    attend_to_sql CHAR(1) NOT NULL CHECK (attend_to_sql IN ('t', 'f'))
);

# --- !Downs

DROP TABLE rdb_group;
DROP TABLE registration;
