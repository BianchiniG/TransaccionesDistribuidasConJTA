
-- Muestra el estado del parametro de configuración para Aceptar transacciones distribuidas 
select * from pg_settings where short_desc like '%prep%trans%'


-- Muestra las transacciones preparadas (distribuidas) que están vigentes, en curso en este momento
select * from pg_prepared_xacts

-- Fuerza el rollback de una transaccion preparada que quedó "colgada" de una aplicación, con 
-- el select anterior se debe ver el valor de la columna  gid
rollback prepared '100_AQ==_Ag=='


