# App prueba tecnica
## _About_
Aplicacion de gestion de productos y ordenes, elaborada bajo una arquitectura de microservicios

Documentacion en Swagger, configurada en la aplicacion

## Instalacion
**Requerimientos**
- Arhivo .jar
- docker file configuracion imagen de aplicacion
- docker-compose file configuracion contenedor y imagen de base de datos

**Instrucciones**
- descargar repositorio desde https://github.com/JavierAvilah/app-linktic
- dentro del repositorio se encuentran los archivos necesarios para crear el contenedor docker de la aplicacion
- ejecutar en consola el comando docker-compose up --build, el comando debe ser ejecutado dentro del directorio donde se decargo el repositorio
- verificar aplicacion ingresando a http://localhost:8080/swagger-ui/index.html#/

**Autenticacion**
Usuario inicial:
```json
{
  "username": "admin",
  "password": "admin"
}

