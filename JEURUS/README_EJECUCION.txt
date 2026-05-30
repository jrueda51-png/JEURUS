PROYECTO JEURUS
Sistema de tickets y soporte al cliente
Java Swing + SQL*Plus + Oracle 10g + JDBC

==================================================
1. RUTA RECOMENDADA EN LA U
==================================================
Descomprimir este ZIP en:

C:\Users\SALA-404\Documents\JEURUS

Repositorio GitHub:
https://github.com/jrueda51-png/JEURUS.git

Usuario GitHub:
jrueda51-png

==================================================
2. BASE DE DATOS ORACLE 10G
==================================================
Nombre del usuario/schema:
JEURUS

Contrasena:
JEURUS

Conexion configurada en:
src\conexion\ConexionBD.java

URL configurada:
jdbc:oracle:thin:@192.168.254.215:1521:orcl

==================================================
3. CREAR USUARIO DE BASE DE DATOS
==================================================
Si el usuario JEURUS no existe, entrar a SQL*Plus como SYSTEM o administrador:

sqlplus system/CLAVE_DEL_SYSTEM

Luego ejecutar:

@C:\Users\SALA-404\Documents\JEURUS\sql\00_crear_usuario_administrador.sql

Si el usuario JEURUS ya existe, omitir este paso.

==================================================
4. CREAR TABLAS, DATOS Y VIEW
==================================================
Entrar en SQL*Plus con el usuario del proyecto:

sqlplus JEURUS/JEURUS

Ejecutar el archivo completo:

@C:\Users\SALA-404\Documents\JEURUS\sql\JEURUS_SQLPLUS_COMPLETO.sql

Este archivo crea:

- Tablas: ROL, USUARIO, CATEGORIA, PRIORIDAD, ESTADO, TICKET, COMENTARIO
- Secuencias
- Triggers para autoincremento compatible con Oracle 10g
- Datos de prueba
- Vista: VW_REPORTE_TICKETS

Usuarios de prueba:

Administrador:
Usuario: JEURUS
Clave: JEURUS

Agente:
Usuario: agente
Clave: 1234

Cliente:
Usuario: cliente
Clave: 1234

==================================================
5. DRIVER DE ORACLE
==================================================
Antes de ejecutar el programa, copiar el driver JDBC de Oracle en la carpeta:

lib

Driver recomendado para Oracle 10g:

ojdbc14.jar

Tambien puede funcionar:

ojdbc6.jar

Si NetBeans no lo detecta automaticamente:

1. Clic derecho sobre el proyecto JEURUS.
2. Properties.
3. Libraries.
4. Add JAR/Folder.
5. Seleccionar el archivo ojdbc14.jar u ojdbc6.jar.
6. Apply y OK.

==================================================
6. EJECUTAR EN NETBEANS
==================================================
1. Abrir Apache NetBeans IDE.
2. File > Open Project.
3. Seleccionar la carpeta JEURUS.
4. Verificar que el paquete conexion tenga ConexionBD.java.
5. Verificar que el driver ojdbc este en Libraries o en la carpeta lib.
6. Ejecutar app.Main o dar clic en Run Project.

Si aparece error de driver:
No se encontro el driver de Oracle.

Solucion:
Agregar ojdbc14.jar u ojdbc6.jar al proyecto.

Si aparece error de conexion:
Verificar que Oracle este activo y que la IP, puerto y SID sean correctos:
192.168.254.215:1521:orcl

==================================================
7. SUBIR A GITHUB
==================================================
Desde CMD o Git Bash ejecutar:

cd /d C:\Users\SALA-404\Documents\JEURUS
git init
git config user.name "jrueda51-png"
git add .
git commit -m "Proyecto JEURUS sistema de tickets"
git branch -M main
git remote add origin https://github.com/jrueda51-png/JEURUS.git
git push -u origin main

Si el remote ya existe, usar:

git remote set-url origin https://github.com/jrueda51-png/JEURUS.git
git push -u origin main

==================================================
8. ARQUETIPO / ARQUITECTURA UTILIZADA
==================================================
El proyecto usa arquitectura MVC en aplicacion cliente-servidor:

Modelo:
Paquete modelo: Usuario, Ticket, Comentario, ReporteTicket.

Vista:
Paquete vista: LoginFrame, PrincipalFrame, TicketPanel, ReportePanel, UsuarioPanel, CategoriaPanel.

Controlador / Acceso a datos:
Paquete dao: UsuarioDAO, TicketDAO, CatalogoDAO.

Conexion:
Paquete conexion: ConexionBD.

Base de datos:
Oracle 10g con SQL*Plus, tablas, secuencias, triggers y VIEW para reportes.
