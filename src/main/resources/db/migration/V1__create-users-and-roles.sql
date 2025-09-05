-- Table Definition
CREATE TABLE "public"."roles" (
    "id" int4 GENERATED ALWAYS AS IDENTITY,
    "name" varchar(50) NOT NULL,
    PRIMARY KEY ("id")
);

-- Indices
CREATE UNIQUE INDEX roles_name_key ON public.roles USING btree (name);

-- Table Definition
CREATE TABLE "public"."users" (
    "role_id" int4 NOT NULL,
    "birth_date" date NOT NULL,
    "document_number" int8 NOT NULL,
    "phone" varchar(20) NOT NULL,
    "lastname" varchar(50) NOT NULL,
    "name" varchar(50) NOT NULL,
    "email" varchar(100) NOT NULL,
    "password" varchar NOT NULL,
    CONSTRAINT "fkp56c1712k691lhsyewcssf40f" FOREIGN KEY ("role_id") REFERENCES "public"."roles"("id"),
    PRIMARY KEY ("document_number")
);

-- Indices
CREATE UNIQUE INDEX users_email_key ON public.users USING btree (email);