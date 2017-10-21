--DROP TABLE cuentas

create role cristian LOGIN password 'cristian'

CREATE TABLE cuentas (
    id integer NOT NULL,
    titular character(20) NOT NULL,
    fecha_creacion timestamp with time zone DEFAULT now()
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
GRANT SELECT,INSERT ON TABLE cuentas TO cristian;
GRANT ALL ON SEQUENCE cuentas_id_seq TO postgres;
GRANT USAGE ON SEQUENCE cuentas_id_seq TO cristian;

-- Para mirar antes y despues de correr "ejemplo1.java"
select * from cuentas
