# Prueba practica NTT DATA

Proyecto Java con Spring Boot

### Requisitos:
- Java Jdk 11
- Base de datos Postgresql v14
- Postman v9.14.13
- Maven 3.8.4

### Proceso de despliegue:
- Modiciar el archivo application.properties con los datos de coneccion de la base de datos.
- Ejecutar el Script BaseDatos.sql ubicado en la raiz del proyecto.
- En la raiz del proyecto ejecutar (mvn clean install)
- Subir el compilado ({RAIZ_DEL_PRPYECTO}\target\prueba-practica-nttdata-0.0.1-SNAPSHOT.war) a Docker
- Importar el Json POSTMAN-NTTDATA.PruebaPractica.json ubicado en la raiz del proyecto.
