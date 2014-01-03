# Tasks schema
 
# --- !Ups
CREATE TABLE acceptances (
  id SERIAL,
  name character varying(50) NOT NULL
);

CREATE TABLE availabilities (
  acceptance_id integer not null default currval('acceptances_id_seq'), 
  date character varying(50) not null,
  available boolean not null,
  PRIMARY KEY (acceptance_id, date)
); 

CREATE TABLE activities (
  acceptance_id integer not null default currval('acceptances_id_seq'), 
  name character varying(50) not null,
  acceptable boolean not null,
  PRIMARY KEY (acceptance_id, name)
); 


# --- !Downs
DROP TABLE availabilities;
DROP TABLE acceptances;
DROP TABLE activities;
