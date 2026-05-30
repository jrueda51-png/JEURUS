PROMPT Ejecutar este archivo conectado como SYSTEM o un usuario administrador.
PROMPT Si el usuario JEURUS ya existe, puede omitir este archivo.

CREATE USER JEURUS IDENTIFIED BY JEURUS;
GRANT CONNECT, RESOURCE TO JEURUS;
GRANT CREATE VIEW TO JEURUS;

PROMPT Usuario JEURUS creado correctamente.
