## Requisitos

- Java: 17.0.6
- Version de Spring boot: v3.2.4
- Database: H2

## Ejecución de aplicación

1) `./gradlew clean build`
   (Este comando descargará todas las dependencias definidas en build.gradle
   y compila el proyecto).
2) `./gradlew bootRun`
   (Este comando ejecuta el proyecto).

## Notas
- La base de datos es generada automaticamente por Hibernate basándose en las entidades, esto al ejecutar la aplicacion.
- El proyecto usa Spring Security y JWT. Para esto cuenta con un Component DataLoader que se encarga de crear al usuario
master o inicial al momento de ejecutar la aplicación con el fin de poder autenticarse y crear usuarios usando Spring Security
  (solo a modo de prueba).
- Los datos de autenticación del usuario inicial se configuran en el archivo aplication.properties