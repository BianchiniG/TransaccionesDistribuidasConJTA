﻿--DROP TABLE cuentas

create role alan LOGIN password 'alan'

CREATE TABLE cuentas (
    id integer NOT NULL,
    titular character(20) NOT NULL,
    fecha_creacion timestamp with time zone DEFAULT now(),
    bloqueada boolean,
    saldo integer CONSTRAINT CHK_saldo CHECK (saldo >= 0)
);
ALTER TABLE public.cuentas OWNER TO postgres;

CREATE SEQUENCE cuentas_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
ALTER TABLE public.cuentas_id_seq OWNER TO postgres;
ALTER SEQUENCE cuentas_id_seq OWNED BY cuentas.id;
ALTER TABLE ONLY cuentas ALTER COLUMN id SET DEFAULT nextval('cuentas_id_seq'::regclass);
GRANT ALL ON TABLE cuentas TO postgres;
GRANT SELECT,INSERT ON TABLE cuentas TO alan;
GRANT ALL ON SEQUENCE cuentas_id_seq TO postgres;
GRANT USAGE ON SEQUENCE cuentas_id_seq TO alan;

INSERT into cuentas VALUES ("Carlos",FALSE,200),("Carla",FALSE,300),("Pedro",TRUE,400),("Julieta",FALSE,500);

-- Para mirar antes y despues de correr "ejemplo1.java"
select * from cuentas
