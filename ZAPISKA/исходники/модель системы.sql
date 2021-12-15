-- Database generated with pgModeler (PostgreSQL Database Modeler).
-- pgModeler  version: 0.9.3
-- PostgreSQL version: 13.0
-- Project Site: pgmodeler.io
-- Model Author: ---

-- Database creation must be performed outside a multi lined SQL file. 
-- These commands were put in this file only as a convenience.
-- 
-- object: new_database | type: DATABASE --
-- DROP DATABASE IF EXISTS new_database;
CREATE DATABASE new_database;
-- ddl-end --


-- object: admin | type: SCHEMA --
-- DROP SCHEMA IF EXISTS admin CASCADE;
CREATE SCHEMA admin;
-- ddl-end --
ALTER SCHEMA admin OWNER TO postgres;
-- ddl-end --

SET search_path TO pg_catalog,public,admin;
-- ddl-end --

-- object: public.admin | type: TABLE --
-- DROP TABLE IF EXISTS public.admin CASCADE;
CREATE TABLE public.admin (
	id integer NOT NULL,
	login varchar NOT NULL,
	password varchar NOT NULL,
	CONSTRAINT admin_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.admin OWNER TO postgres;
-- ddl-end --

-- object: public.customers | type: TABLE --
-- DROP TABLE IF EXISTS public.customers CASCADE;
CREATE TABLE public.customers (
	name varchar NOT NULL,
	adress varchar NOT NULL,
	telephone varchar NOT NULL,
	mail varchar NOT NULL,
	number varchar NOT NULL,
	name_projects varchar,
	customer_projects varchar,
	CONSTRAINT customers_pk PRIMARY KEY (name)

);
-- ddl-end --
ALTER TABLE public.customers OWNER TO postgres;
-- ddl-end --

-- object: public.otchet | type: TABLE --
-- DROP TABLE IF EXISTS public.otchet CASCADE;
CREATE TABLE public.otchet (
	name varchar NOT NULL,
	risks varchar NOT NULL,
	participants varchar NOT NULL,
	deadline varchar NOT NULL,
	CONSTRAINT otchet_pk PRIMARY KEY (name)

);
-- ddl-end --
ALTER TABLE public.otchet OWNER TO postgres;
-- ddl-end --

-- object: public.projects | type: TABLE --
-- DROP TABLE IF EXISTS public.projects CASCADE;
CREATE TABLE public.projects (
	id integer NOT NULL,
	name varchar NOT NULL,
	customer varchar NOT NULL,
	cost varchar NOT NULL,
	deadline varchar NOT NULL,
	level varchar NOT NULL,
	name_otchet varchar,
	CONSTRAINT projects_pk PRIMARY KEY (name,customer)

);
-- ddl-end --
ALTER TABLE public.projects OWNER TO postgres;
-- ddl-end --

-- object: otchet_fk | type: CONSTRAINT --
-- ALTER TABLE public.projects DROP CONSTRAINT IF EXISTS otchet_fk CASCADE;
ALTER TABLE public.projects ADD CONSTRAINT otchet_fk FOREIGN KEY (name_otchet)
REFERENCES public.otchet (name) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: projects_uq | type: CONSTRAINT --
-- ALTER TABLE public.projects DROP CONSTRAINT IF EXISTS projects_uq CASCADE;
ALTER TABLE public.projects ADD CONSTRAINT projects_uq UNIQUE (name_otchet);
-- ddl-end --

-- object: public.users | type: TABLE --
-- DROP TABLE IF EXISTS public.users CASCADE;
CREATE TABLE public.users (
	id integer NOT NULL,
	name varchar NOT NULL,
	surname varchar NOT NULL,
	login varchar NOT NULL,
	password varchar NOT NULL,
	CONSTRAINT users_pk PRIMARY KEY (id)

);
-- ddl-end --
ALTER TABLE public.users OWNER TO postgres;
-- ddl-end --

-- object: projects_fk | type: CONSTRAINT --
-- ALTER TABLE public.customers DROP CONSTRAINT IF EXISTS projects_fk CASCADE;
ALTER TABLE public.customers ADD CONSTRAINT projects_fk FOREIGN KEY (name_projects,customer_projects)
REFERENCES public.projects (name,customer) MATCH FULL
ON DELETE SET NULL ON UPDATE CASCADE;
-- ddl-end --

-- object: customers_uq | type: CONSTRAINT --
-- ALTER TABLE public.customers DROP CONSTRAINT IF EXISTS customers_uq CASCADE;
ALTER TABLE public.customers ADD CONSTRAINT customers_uq UNIQUE (name_projects,customer_projects);
-- ddl-end --


