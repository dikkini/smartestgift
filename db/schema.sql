SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET search_path = PUBLIC, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = FALSE;

DROP TABLE public.userGift CASCADE;
DROP TABLE public.giftCategory CASCADE;
DROP TABLE public.gift CASCADE;
DROP TABLE public.role CASCADE;
DROP TABLE public.user CASCADE;

CREATE TABLE public.gift
(
  uuid        UUID UNIQUE PRIMARY KEY NOT NULL,
  name        VARCHAR(255)            NOT NULL,
  description TEXT,
  categoryId  INT                     NOT NULL
);

CREATE TABLE public.role (
  id   SERIAL PRIMARY KEY,
  role VARCHAR(20) NOT NULL
);

CREATE TABLE public.user
(
  uuid             UUID PRIMARY KEY         NOT NULL,
  login            VARCHAR(20)              NOT NULL,
  passwordMd5      VARCHAR(32)              NOT NULL,
  firstName        VARCHAR(255)             NOT NULL,
  lastName         VARCHAR(255)             NOT NULL,
  middleName       VARCHAR(255),
  birthDate        TIMESTAMP                NOT NULL,
  registrationDate TIMESTAMP                NOT NULL,
  roleId           INT REFERENCES role (id) NOT NULL
);

CREATE TABLE public.userGift
(
  userUuid UUID REFERENCES "user" (uuid) NOT NULL,
  giftUuid UUID REFERENCES gift (uuid)   NOT NULL,
  PRIMARY KEY (userUuid, giftUuid)
);

CREATE TABLE public.giftCategory
(
  id          SERIAL PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  description VARCHAR(255) NOT NULL
);



