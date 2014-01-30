SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET search_path = PUBLIC, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = FALSE;

DROP TABLE public.users CASCADE;
DROP TABLE public.role CASCADE;
DROP TABLE public.user_details CASCADE;
DROP TABLE public.persistent_login CASCADE;

CREATE TABLE public.users
(
  uuid       VARCHAR(36) PRIMARY KEY NOT NULL,
  firstName  VARCHAR(255)            NOT NULL,
  lastName   VARCHAR(255)            NOT NULL,
  middleName VARCHAR(255),
  birthDate  TIMESTAMP               NOT NULL
);

CREATE TABLE public.role (
  id   SERIAL PRIMARY KEY,
  role VARCHAR(20) NOT NULL
);

CREATE TABLE public.user_details
(
  userUuid              VARCHAR(36) PRIMARY KEY  REFERENCES public.users (uuid) NOT NULL,
  login                 VARCHAR(64) UNIQUE                                     NOT NULL,
  passwordMd5           VARCHAR(32)                                            NOT NULL,
  enabled               BOOLEAN DEFAULT FALSE                                  NOT NULL,
  roleId                INT REFERENCES public.role (id)                        NOT NULL,
  accountNonExpired     BOOLEAN DEFAULT TRUE                                   NOT NULL,
  credentialsNonExpired BOOLEAN DEFAULT TRUE                                   NOT NULL,
  accountNonLocked      BOOLEAN DEFAULT TRUE                                   NOT NULL,
  registrationDate      TIMESTAMP DEFAULT now()                                NOT NULL
);

CREATE TABLE public.persistent_login
(
  username    VARCHAR(64) REFERENCES public.user_details (login) NOT NULL,
  series   VARCHAR(64) PRIMARY KEY                            NOT NULL,
  token    VARCHAR(64) DEFAULT NULL,
  lastUsed TIMESTAMP                                          NOT NULL
);
