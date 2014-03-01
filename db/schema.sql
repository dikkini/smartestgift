SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET search_path = PUBLIC, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = FALSE;

DROP TABLE public.users CASCADE;
DROP TABLE public.auth_provider CASCADE;
DROP TABLE public.role CASCADE;
DROP TABLE public.user_details CASCADE;
DROP TABLE public.persistent_login CASCADE;
DROP TABLE public.gift CASCADE;
DROP TABLE public.user_gift CASCADE;
DROP TABLE public.gift_category CASCADE;
DROP TABLE public.file_type CASCADE;
DROP TABLE public.file CASCADE;
DROP TABLE public.gift_file CASCADE;

CREATE TABLE public.auth_provider
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL
);

CREATE TABLE public.file_type
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  path VARCHAR NOT NULL
);

CREATE TABLE public.role (
  id   SERIAL PRIMARY KEY,
  role VARCHAR(20) NOT NULL
);

CREATE TABLE public.file
(
  id      SERIAL PRIMARY KEY,
  name    VARCHAR                              NOT NULL,
  size    VARCHAR                              NOT NULL,
  type_id INT REFERENCES public.file_type (id) NOT NULL
);

CREATE TABLE public.users
(
  uuid              VARCHAR(36) PRIMARY KEY NOT NULL,
  username          VARCHAR(64) UNIQUE      NOT NULL,
  first_name        VARCHAR(255)            NOT NULL,
  last_name         VARCHAR(255),
  middle_name       VARCHAR(255),
  birth_date        TIMESTAMP,
  gender            BOOLEAN,
  address           TEXT,
  address_visible   BOOLEAN DEFAULT FALSE   NOT NULL,
  profile_visible   BOOLEAN DEFAULT TRUE    NOT NULL,
  cellphone         VARCHAR(255),
  cellphone_visible BOOLEAN DEFAULT FALSE   NOT NULL,
  file_id           INT REFERENCES public.file (id)
);

CREATE TABLE public.user_details
(
  user_uuid             VARCHAR(36) PRIMARY KEY  REFERENCES public.users (uuid) NOT NULL,
  username              VARCHAR(64) UNIQUE REFERENCES public.users (username)   NOT NULL,
  password              TEXT,
  email                 VARCHAR UNIQUE                                          NOT NULL,
  enabled               BOOLEAN DEFAULT TRUE                                    NOT NULL,
  social_id             VARCHAR,
  auth_provider_id      INT REFERENCES public.auth_provider (id)                NOT NULL,
  role_id               INT REFERENCES public.role (id)                         NOT NULL,
  accountNonExpired     BOOLEAN DEFAULT TRUE                                    NOT NULL,
  credentialsNonExpired BOOLEAN DEFAULT TRUE                                    NOT NULL,
  accountNonLocked      BOOLEAN DEFAULT TRUE                                    NOT NULL,
  registration_date     TIMESTAMP DEFAULT now()                                 NOT NULL
);

CREATE TABLE public.persistent_login
(
  username  VARCHAR(64) UNIQUE REFERENCES public.users (username) NOT NULL,
  series    VARCHAR(64) PRIMARY KEY                               NOT NULL,
  token     VARCHAR(64) DEFAULT NULL,
  last_used TIMESTAMP                                             NOT NULL
);

CREATE TABLE public.gift
(
  uuid        VARCHAR(36) UNIQUE PRIMARY KEY NOT NULL,
  name        VARCHAR(255)                   NOT NULL,
  cost        INT                            NOT NULL,
  description TEXT,
  category_id INT                            NOT NULL,
  add_date    TIMESTAMP                      NOT NULL
);


CREATE TABLE public.gift_file
(
  id        SERIAL PRIMARY KEY,
  file_id   INT REFERENCES public.file (id)       NOT NULL,
  gift_uuid VARCHAR REFERENCES public.gift (uuid) NOT NULL
);

CREATE TABLE public.user_gift
(
  user_uuid    VARCHAR(36) REFERENCES public.users (uuid)  NOT NULL,
  gift_uuid    VARCHAR(36) REFERENCES public.gift (uuid)   NOT NULL,
  moneyCollect INT                                         NOT NULL,
  PRIMARY KEY (user_uuid, gift_uuid)
);

CREATE TABLE public.gift_category
(
  id          SERIAL PRIMARY KEY,
  name        VARCHAR(255)                    NOT NULL,
  description VARCHAR(255)                    NOT NULL,
  file_id     INT REFERENCES public.file (id) NOT NULL
);
