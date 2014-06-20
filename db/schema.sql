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
DROP TABLE public.user_roles CASCADE;
DROP TABLE public.user_friends CASCADE;
DROP TABLE public.persistent_login CASCADE;
DROP TABLE public.shop CASCADE;
DROP TABLE public.gift CASCADE;
DROP TABLE public.gift_shop CASCADE;
DROP TABLE public.user_gifts CASCADE;
DROP TABLE public.gift_category CASCADE;
DROP TABLE public.file_type CASCADE;
DROP TABLE public.file CASCADE;
DROP TABLE public.gift_files CASCADE;
DROP TABLE public.message CASCADE;
DROP TABLE public.conversation CASCADE;
DROP TABLE public.message_status CASCADE;

CREATE TABLE public.role
(
  id SERIAL PRIMARY KEY,
  role VARCHAR(36) NOT NULL
);

CREATE TABLE public.message_status
(
  id     SERIAL PRIMARY KEY,
  status VARCHAR(20) NOT NULL
);

CREATE TABLE public.file_type
(
  id   SERIAL PRIMARY KEY,
  name VARCHAR NOT NULL,
  path VARCHAR NOT NULL
);

CREATE TABLE public.file
(
  id      SERIAL PRIMARY KEY,
  name    VARCHAR                              NOT NULL,
  type_id INT REFERENCES public.file_type (id) NOT NULL
);

CREATE TABLE public.users
(
  uuid              VARCHAR(36) PRIMARY KEY                     NOT NULL,
  username          VARCHAR(45) UNIQUE                          NOT NULL,
  first_name        VARCHAR(255)                                NOT NULL,
  last_name         VARCHAR(255),
  middle_name       VARCHAR(255),
  birth_date        TIMESTAMP,
  gender            BOOLEAN,
  address           TEXT,
  address_visible   BOOLEAN DEFAULT FALSE                       NOT NULL,
  profile_visible   BOOLEAN DEFAULT TRUE                        NOT NULL,
  cellphone         VARCHAR(255),
  cellphone_visible BOOLEAN DEFAULT FALSE                       NOT NULL,
  file_id           INT REFERENCES public.file (id) DEFAULT 10  NOT NULL,
  registration_date TIMESTAMP DEFAULT now()                     NOT NULL,
  social_id         VARCHAR,
  auth_provider_id  INT                                         NOT NULL,
  email             VARCHAR UNIQUE                              NOT NULL,
  password          TEXT,
  enabled           BOOLEAN DEFAULT TRUE                        NOT NULL
);

CREATE TABLE public.user_roles
(
  uuid     VARCHAR(36) PRIMARY KEY,
  username VARCHAR(45) REFERENCES public.users (username) NOT NULL,
  role     VARCHAR(45)                                    NOT NULL
);

CREATE TABLE public.user_friends
(
  user_uuid     VARCHAR(36) REFERENCES public.users (uuid) NOT NULL,
  friend_uuid   VARCHAR(36) REFERENCES public.users (uuid) NOT NULL,
  friendAddDate TIMESTAMP                                  NOT NULL,
  friendTypeId  INT                                        NOT NULL,
  PRIMARY KEY (user_uuid, friend_uuid)
);

CREATE TABLE public.persistent_login
(
  username  VARCHAR(64) UNIQUE REFERENCES public.users (username)  NOT NULL,
  series    VARCHAR(64) PRIMARY KEY                                NOT NULL,
  token     VARCHAR(64) DEFAULT NULL,
  last_used TIMESTAMP                                              NOT NULL
);

CREATE TABLE public.shop
(
  uuid        VARCHAR(36) PRIMARY KEY,
  name        VARCHAR(255) NOT NULL,
  description VARCHAR(255)
);

CREATE TABLE public.gift
(
  uuid        VARCHAR(36) UNIQUE PRIMARY KEY            NOT NULL,
  name        VARCHAR(255)                              NOT NULL,
  description TEXT,
  category_id INT                                       NOT NULL,
  add_date    TIMESTAMP                                 NOT NULL
);

CREATE TABLE public.gift_shop
(
  uuid      VARCHAR(36) PRIMARY KEY,
  shop_uuid VARCHAR(36) REFERENCES public.shop (uuid)   NOT NULL,
  gift_uuid VARCHAR(36) REFERENCES public.gift (uuid)   NOT NULL,
  price     DECIMAL                                     NOT NULL,
  discount  INT DEFAULT 0
);

CREATE TABLE public.gift_files
(
  id        SERIAL PRIMARY KEY,
  file_id   INT REFERENCES public.file (id)       NOT NULL,
  gift_uuid VARCHAR REFERENCES public.gift (uuid) NOT NULL
);

CREATE TABLE public.user_gifts
(
  user_uuid      VARCHAR(36) REFERENCES public.users (uuid)        NOT NULL,
  gift_shop_uuid VARCHAR(36) REFERENCES public.gift_shop (uuid)    NOT NULL,
  moneyCollect   INT                                               NOT NULL,
  endDate        TIMESTAMP                                         NOT NULL,
  PRIMARY KEY (user_uuid, gift_shop_uuid)
);

CREATE TABLE public.gift_category
(
  id          SERIAL PRIMARY KEY,
  code        VARCHAR                         NOT NULL,
  name        VARCHAR(255)                    NOT NULL,
  description VARCHAR(255)                    NOT NULL,
  file_id     INT REFERENCES public.file (id) NOT NULL
);


CREATE TABLE public.conversation
(
  uuid           VARCHAR(36) PRIMARY KEY,
  user_from_uuid VARCHAR(36) REFERENCES public.users (uuid)    NOT NULL,
  user_to_uuid   VARCHAR(36) REFERENCES public.users (uuid)    NOT NULL
);

CREATE TABLE public.message
(
  uuid              VARCHAR(36) PRIMARY KEY,
  user_uuid         VARCHAR(36) REFERENCES public.users (uuid)         NOT NULL,
  message           VARCHAR                                            NOT NULL,
  date              TIMESTAMP                                          NOT NULL,
  conversation_uuid VARCHAR(36) REFERENCES public.conversation (uuid)  NOT NULL,
  status_id         INT REFERENCES public.message_status (id)          NOT NULL
);