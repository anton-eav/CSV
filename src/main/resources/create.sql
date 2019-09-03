CREATE TABLE public.units
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    ts timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    series character varying(50) COLLATE pg_catalog."default",
    date_manufacture date,
    inventory_number character varying(30) COLLATE pg_catalog."default",
    code_nomenclature character(5) COLLATE pg_catalog."default" NOT NULL,
    code_predefined character(5) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT pk_units PRIMARY KEY (id),
    CONSTRAINT units_id_code_predefined_key UNIQUE (id, code_predefined),
    CONSTRAINT units_series_code_nomenclature_key UNIQUE (series, code_nomenclature)
);

CREATE TABLE public.sim_cards
(
    id uuid NOT NULL DEFAULT uuid_generate_v4(),
    ts timestamp with time zone NOT NULL DEFAULT CURRENT_TIMESTAMP,
    code_operator character(2) COLLATE pg_catalog."default",
    ip_sim character varying(30) COLLATE pg_catalog."default",
    msisdn character varying(20) COLLATE pg_catalog."default",
    imsi character varying(20) COLLATE pg_catalog."default",
    iccid character varying(20) COLLATE pg_catalog."default",
    code_predefined character(5) COLLATE pg_catalog."default" NOT NULL DEFAULT 'SIM'::bpchar,
    CONSTRAINT pk_sim_cards PRIMARY KEY (id),
    CONSTRAINT sim_cards_id_fkey FOREIGN KEY (code_predefined, id)
        REFERENCES public.units (code_predefined, id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION,
    CONSTRAINT sim_cards_code_predefined_check CHECK (code_predefined = 'SIM'::bpchar)
)