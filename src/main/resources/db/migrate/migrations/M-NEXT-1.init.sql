--liquibase formatted sql

--changeset aries.prayuga@bankmas.co.id:1 labels:points context:research
CREATE TABLE IF NOT EXISTS public.company (
	id varchar(36) NOT NULL,
	company_address varchar(200) NOT NULL,
	company_email varchar(64) NOT NULL,
	company_name varchar(64) NOT NULL,
	created_at timestamp NULL,
	status bool NOT NULL,
	updated_at timestamp NULL,
	CONSTRAINT company_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.roles (
	id varchar(36) NOT NULL,
	"name" varchar(20) NULL,
	CONSTRAINT roles_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.users (
	id varchar(36) NOT NULL,
	email varchar(50) NULL,
	"password" varchar(120) NULL,
	username varchar(20) NULL,
	CONSTRAINT uk6dotkott2kjsp8vw4d0m25fb7 UNIQUE (email),
	CONSTRAINT ukr43af9ap4edm43mmtq01oddj6 UNIQUE (username),
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS public.user_roles (
	user_id varchar(36) NOT NULL,
	role_id varchar(36) NOT NULL,
	CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role_id),
	CONSTRAINT fkh8ciramu9cc9q3qcqiv4ue8a6 FOREIGN KEY (role_id) REFERENCES public.roles(id),
	CONSTRAINT fkhfh9dx7w3ubf1co1vdev94g3f FOREIGN KEY (user_id) REFERENCES public.users(id)
);
--rollback DROP TABLE public.company;
--rollback DROP TABLE public.roles;
--rollback DROP TABLE public.users;
--rollback DROP TABLE public.user_roles;