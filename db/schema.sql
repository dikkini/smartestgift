SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = ON;
SET check_function_bodies = FALSE;
SET client_min_messages = WARNING;
SET search_path = PUBLIC, pg_catalog;
SET default_tablespace = '';
SET default_with_oids = FALSE;

-- this extension allow to generate uuid as default field, gen_random_uuid - function from this extension
-- CREATE EXTENSION IF NOT EXISTS pgcrypto;

DROP TABLE IF EXISTS public.users CASCADE;
DROP TABLE IF EXISTS public.user_social_social_networks CASCADE;
DROP TABLE IF EXISTS public.user_goals CASCADE;
DROP TABLE IF EXISTS public.user_goal CASCADE;
DROP TABLE IF EXISTS public.user_favorite_goals CASCADE;
DROP TABLE IF EXISTS public.user_favorite_goal CASCADE;
DROP TABLE IF EXISTS public.social_networks CASCADE;
DROP TABLE IF EXISTS public.role CASCADE;
DROP TABLE IF EXISTS public.goal_targets CASCADE;
DROP TABLE IF EXISTS public.goal_ref_urls CASCADE;
DROP TABLE IF EXISTS public.goal_ref_personal_url CASCADE;
DROP TABLE IF EXISTS public.goal_files CASCADE;
DROP TABLE IF EXISTS public.goal CASCADE;
DROP TABLE IF EXISTS public.file_type CASCADE;
DROP TABLE IF EXISTS public.file CASCADE;
DROP TABLE IF EXISTS public.currency CASCADE;
DROP TABLE IF EXISTS public.auth_providers CASCADE;

CREATE TABLE public.role
(
    id   SERIAL PRIMARY KEY
  , role UUID NOT NULL
);

CREATE TABLE public.file_type
(
    id   SERIAL PRIMARY KEY
  , name VARCHAR NOT NULL
  , path VARCHAR NOT NULL
);

CREATE TABLE public.file
(
    id     SERIAL PRIMARY KEY
  , name   VARCHAR NOT NULL
  , typeId INT REFERENCES public.file_type (id) NOT NULL
);

CREATE TABLE public.auth_providers
(
    id SERIAL PRIMARY KEY
  , name VARCHAR(50) NOT NULL
);

CREATE TABLE public.currency
(
    id SERIAL PRIMARY KEY
  , name VARCHAR(50)      NOT NULL
);


CREATE TABLE public.users
(
    uuid             UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , username         VARCHAR(45) UNIQUE
  , email            VARCHAR     UNIQUE
  , firstName        VARCHAR(255)
  , lastName         VARCHAR(255)
  , middleName       VARCHAR(255)
  , birthDate        TIMESTAMP
  , gender           BOOLEAN
  , roleId           INT REFERENCES public.role(id)
  , address          TEXT
  , addressVisible   BOOLEAN NOT NULL
  , profileVisible   BOOLEAN NOT NULL
  , cellphone        VARCHAR(255)
  , cellphoneVisible BOOLEAN NOT NULL
  , photoId          INT REFERENCES public.file(id) NOT NULL
  , registrationDate TIMESTAMP NOT NULL
  , password         TEXT
  , enabled          BOOLEAN NOT NULL
);

CREATE TABLE public.social_networks
(
    uuid           UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , socialId       VARCHAR(255)
  , authProviderId INT REFERENCES public.auth_providers(id) NOT NULL
  -- больше, больше полей с информации из соц сети
);

CREATE TABLE public.user_social_social_networks
(
    socialNetworkUuid UUID REFERENCES public.social_networks(uuid) NOT NULL
  , userUuid          UUID REFERENCES public.users(uuid) NOT NULL
);

CREATE TABLE goal_targets -- таблица с товарами/услугами на которые создаются цели (бабло сюда не включено или включено?)
(
    uuid UUID UNIQUE PRIMARY KEY NOT NULL
  -- пока не понятно какие еще поля могут быть у конечного товара/услуги для цели
);

CREATE TABLE goal_ref_personal_url -- таблица с персонифицрованными ref ссылками на цель для друзей из соц сети
(
    uuid UUID UNIQUE PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , url VARCHAR(60) NOT NULL
  , authProvider INT REFERENCES public.auth_providers(id) NOT NULL
  -- еще информация о жертве
);

-- цель
CREATE TABLE public.goal
(
    uuid        UUID UNIQUE PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , billNumber  UUID UNIQUE NOT NULL
  , endDate     TIMESTAMP
  , endSum      DECIMAL
  , name        VARCHAR(255)
  , description VARCHAR(255)
  , price       DECIMAL NOT NULL
  , currencyId  INT  REFERENCES public.currency(id) NOT NULL
  , target      UUID REFERENCES public.goal_targets(uuid)  -- ради чего цель, айфон, бабло - в случае бабла - таргет пустой (?)
);

-- ref ссылки на цель
CREATE TABLE goal_ref_urls (
    uuid UUID UNIQUE PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , goalRefURL VARCHAR(60) -- общая ссылка на цель
  , goalPersonalUrlUuid UUID REFERENCES public.goal_ref_personal_url(uuid) -- ссылка на таблицу с информацией о ref жертве, их может быть много
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
);

-- фоточки цели
CREATE TABLE public.goal_files
(
    id       SERIAL  PRIMARY KEY
  , fileId   INT     REFERENCES public.file(id) NOT NULL
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
);

-- пользовательские цели
CREATE TABLE public.user_goal
(
    uuid     UUID PRIMARY KEY DEFAULT gen_random_uuid()
  , userUuid UUID REFERENCES public.users(uuid) NOT NULL
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
);

-- избранные пользовательские цели
CREATE TABLE public.user_favorite_goal
(
    uuid     UUID PRIMARY KEY DEFAULT gen_random_uuid()
  , userUuid UUID REFERENCES public.users(uuid) NOT NULL
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
);

