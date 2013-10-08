SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: -
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: -
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;


CREATE TABLE public.gift
(
    uuid UUID UNIQUE PRIMARY KEY NOT NULL,
    name varchar (255) NOT NULL,
    description text,
    priceid INT NOT NULL,
    categoryid INT NOT NULL
);


CREATE TABLE public.client
(
  uuid UUID UNIQUE NOT NULL,
  login VARCHAR (16) NOT NULL,
  passwordmd5 text NOT NULL,
  firstname varchar (255) NOT NULL,
  lastname varchar (255) NOT NULL,
  middlename varchar (255),
  birthdate timestamp NOT NULL,
  photo bytea,
  regdate timestamp  NOT NULL,
  PRIMARY KEY ( uuid, login )
);

CREATE TABLE public.clientgift
(
  id SERIAL UNIQUE NOT NULL,
  clientuuid uuid REFERENCES client(uuid) NOT NULL,
  giftuuid uuid REFERENCES gift(uuid) NOT NULL,
    PRIMARY KEY ( id )
);

CREATE TABLE public.giftcategory
(
  id SERIAL PRIMARY KEY,
  name varchar(255) NOT NULL,
  description varchar (255) NOT NULL
);

CREATE TABLE public.shop
(
  uuid UUID UNIQUE PRIMARY KEY NOT NULL,
  name varchar (255) NOT NULL,
  description varchar(255) NOT NULL
);

CREATE TABLE public.transaction
(
  uuid UUID UNIQUE PRIMARY KEY NOT NULL,
  clientuuid UUID REFERENCES client(uuid),
  giftuuid UUID REFERENCES gift(uuid),
  status BOOLEAN NOT NULL
);


