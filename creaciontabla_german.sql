--DROP TABLE cuentas

create role german LOGIN password 'german'

CREATE TABLE cuentas (
    id integer NOT NULL,
    titular character(20) NOT NULL,
    fecha_creacion timestamp with time zone DEFAULT now()
    bloqueada boolean,
    saldo integer CONSTRAINT CHK_saldo CHECK (saldo>=0)
);

ALTER TABLE public.cuentas OWNER TO postgres;

CREATE SEQUENCE cuentas_id_seq
    START WITH 3000
    INCREMENT BY 1
    NO MINVALUE
    MAXVALUE 3999
    CACHE 1;
ALTER TABLE public.cuentas_id_seq OWNER TO postgres;
ALTER SEQUENCE cuentas_id_seq OWNED BY cuentas.id;
ALTER TABLE ONLY cuentas ALTER COLUMN id SET DEFAULT nextval('cuentas_id_seq'::regclass);
GRANT ALL ON TABLE cuentas TO postgres;
GRANT SELECT,INSERT ON TABLE cuentas TO german;
GRANT ALL ON SEQUENCE cuentas_id_seq TO postgres;
GRANT USAGE ON SEQUENCE cuentas_id_seq TO german;

INSERT INTO cuentas  VALUES (DEFAULT,'Ester',DEFAULT, TRUE, 985), (DEFAULT,'Azucena',DEFAULT, FALSE, 400), (DEFAULT,'Amanda',DEFAULT, FALSE, 200), (DEFAULT,'Diego',DEFAULT,FALSE,400);

-- Para mirar antes y despues de correr "ejemplo1.java"
select * from cuentas
