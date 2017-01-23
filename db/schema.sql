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

DROP TABLE IF EXISTS public.user CASCADE;
DROP TABLE IF EXISTS public.privilege CASCADE;
DROP TABLE IF EXISTS public.roles_privilege CASCADE;
DROP TABLE IF EXISTS public.user_social_social_network CASCADE;
DROP TABLE IF EXISTS public.user_goals CASCADE;
DROP TABLE IF EXISTS public.user_goal CASCADE;
DROP TABLE IF EXISTS public.social_network CASCADE;
DROP TABLE IF EXISTS public.user_pay_to_goal CASCADE;
DROP TABLE IF EXISTS public.user_favorite_goal CASCADE;
DROP TABLE IF EXISTS public.social_networks CASCADE;
DROP TABLE IF EXISTS public.role CASCADE;
DROP TABLE IF EXISTS public.target CASCADE;
DROP TABLE IF EXISTS public.goal_ref_url CASCADE;
DROP TABLE IF EXISTS public.goal_ref_personal_url CASCADE;
DROP TABLE IF EXISTS public.goal_file CASCADE;
DROP TABLE IF EXISTS public.goal CASCADE;
DROP TABLE IF EXISTS public.file CASCADE;
DROP TABLE IF EXISTS public.currency CASCADE;
DROP TABLE IF EXISTS public.auth_provider CASCADE;

CREATE TABLE public.role
(
    id   SERIAL PRIMARY KEY
  , name VARCHAR(30)
);

CREATE TABLE public.privilege
(
    id   SERIAL PRIMARY KEY
  , name VARCHAR(36) NOT NULL
);

CREATE TABLE public.roles_privilege
(
    roleId      INT REFERENCES public.role(id)    NOT NULL
  , privilegeId INT REFERENCES public.privilege(id) NOT NULL
);

CREATE TABLE public.file
(
    id     SERIAL PRIMARY KEY
  , name   VARCHAR NOT NULL
  , extension VARCHAR(10) NOT NULL
  , mimeType VARCHAR(100) NOT NULL
  , size FLOAT NOT NULL
);

CREATE TABLE public.auth_provider
(
    id SERIAL PRIMARY KEY
  , name VARCHAR(50) NOT NULL
);

CREATE TABLE public.currency
(
    id SERIAL PRIMARY KEY
  , sign VARCHAR(3) NOT NULL
);

CREATE TABLE public.user
(
    uuid UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , username VARCHAR(45) UNIQUE
  , email VARCHAR(255) UNIQUE
  , firstName VARCHAR(255)
  , lastName VARCHAR(255)
  , middleName VARCHAR(255)
  , birthDate DATE
  , gender BOOLEAN
  , roleId INT REFERENCES public.role(id)
  , address TEXT
  , addressVisible BOOLEAN
  , profileVisible BOOLEAN
  , cellphone VARCHAR(255)
  , cellphoneVisible BOOLEAN
  , photoId INT REFERENCES public.file(id) DEFAULT 0
  , registrationDate TIMESTAMP
  , password TEXT
  , enabled BOOLEAN NOT NULL
  , accountNonExpired BOOLEAN NOT NULL
  , accountNonLocked  BOOLEAN NOT NULL
  , credentialsNonExpired BOOLEAN NOT NULL
);

CREATE TABLE public.social_network
(
    uuid           UUID PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , socialId       VARCHAR(255)
  , authProviderId INT REFERENCES public.auth_provider(id) NOT NULL
  -- больше, больше полей с информации из соц сети
);

CREATE TABLE public.user_social_social_network
(
    socialNetworkUuid UUID REFERENCES public.social_network(uuid) NOT NULL
  , userUuid          UUID REFERENCES public.user(uuid) NOT NULL
);

CREATE TABLE target -- таблица с товарами/услугами на которые создаются цели (бабло сюда не включено или включено?)
(
    uuid UUID UNIQUE PRIMARY KEY NOT NULL
  , name VARCHAR(255) NOT NULL
  -- пока не понятно какие еще поля могут быть у конечного товара/услуги для цели
);

-- ref ссылки на цель
CREATE TABLE goal_ref_url
(
    uuid UUID UNIQUE PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , refURL VARCHAR(60) -- общая ссылка на цель
);

-- цель
CREATE TABLE public.goal
(
    uuid        UUID UNIQUE PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , billNumber  UUID UNIQUE NOT NULL
  , startDate   TIMESTAMP NOT NULL
  , endDate     TIMESTAMP
  , startSum    DECIMAL NOT NULL
  , endSum      DECIMAL
  , name        VARCHAR(255)
  , description VARCHAR(4000)
  , price       DECIMAL NOT NULL
  , currencyId  INT  REFERENCES public.currency(id) NOT NULL
  , goalRefURLUuid UUID REFERENCES public.goal_ref_url(uuid)
  , targetUuid  UUID REFERENCES public.target(uuid) NOT NULL -- ради чего цель, айфон, бабло - в случае бабла - таргет пустой (?)
  , isPrivate     BOOLEAN
);

CREATE TABLE goal_ref_personal_url -- таблица с персонифицрованными ref ссылками на цель для друзей из соц сети
(
    uuid UUID UNIQUE PRIMARY KEY DEFAULT gen_random_uuid() NOT NULL
  , url VARCHAR(60) NOT NULL
  , authProviderId INT REFERENCES public.auth_provider(id) NOT NULL
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
  -- еще информация о жертве
);

-- фоточки цели
CREATE TABLE public.goal_file
(
    id       SERIAL  PRIMARY KEY
  , fileId   INT     REFERENCES public.file(id) NOT NULL
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
);

-- пользовательские цели
CREATE TABLE public.user_goal
(
    uuid     UUID PRIMARY KEY DEFAULT gen_random_uuid()
  , userUuid UUID REFERENCES public.user(uuid) NOT NULL
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
);

CREATE TABLE public.user_pay_to_goal
(
    uuid     UUID PRIMARY KEY DEFAULT gen_random_uuid()
  , userUuid UUID REFERENCES public.user(uuid) NOT NULL
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
  , moneySum DECIMAL NOT NULL
  , currencyId  INT  REFERENCES public.currency(id) NOT NULL
  , comment VARCHAR(3000)
);

-- избранные пользовательские цели
CREATE TABLE public.user_favorite_goal
(
    uuid     UUID PRIMARY KEY DEFAULT gen_random_uuid()
  , userUuid UUID REFERENCES public.user(uuid) NOT NULL
  , goalUuid UUID REFERENCES public.goal(uuid) NOT NULL
);

