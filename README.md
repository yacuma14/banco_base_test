# Banco Base - README

Este README recoge cómo ejecutar y verificar la aplicación, la documentación API (Swagger/OpenAPI), el diagrama entidad-relación del paquete `banco.test.repository`, la conexión a la base de datos y la información de Kafka.

Requisitos previos
- Java 17
- Maven
- Docker y Docker Compose (si se usa la composición incluida)

1) Levantar los servicios Docker (MySQL, Zookeeper, Kafka, Kafka-UI)

Abra PowerShell en el directorio raíz del proyecto (donde está `docker-compose.yml`) y ejecute:

```powershell  
Set-Location "{bancobase_path_repository}\banco_base_test"  
docker-compose up -d  
```  

Comandos útiles:
- Ver contenedores: `docker ps -a`
- Ver logs: `docker-compose logs -f`
- Listar topics desde el contenedor broker:
  ```powershell  
  docker-compose exec broker kafka-topics --bootstrap-server broker:29092 --list  
 ```  
2) Compilar y ejecutar la aplicación localmente  
  
Construir el jar con Maven y ejecutarlo:  
  
```powershell  
mvn clean compile package  
java -jar target\bancobase-0.0.1-SNAPSHOT.jar  
```  

Alternativamente puede ejecutar directamente con Maven:

```powershell  
mvn clean compile package spring-boot:run  
```  

3) URL de Swagger / OpenAPI

- Swagger UI (interfaz): http://localhost:8081/swagger-ui/index.html
- URL que solicitaste (compatibilidad/alias): http://localhost:8081/swagger-ui.html#/
- Especificación OpenAPI (JSON): http://localhost:8081/v3/api-docs

Si la UI no aparece, asegúrate de que la aplicación está arrancada y que el puerto en `src/main/resources/application.properties` es `8081`.

4) Endpoints (basado en `banco.test.controller.PagoController`)

La API expone los siguientes endpoints bajo el prefijo `/api/v1`:

- POST /api/v1/pago
- GET /api/v1/pago/{pagosUsuario}/obtener-all
- GET /api/v1/pago/{idPago}/obtener
- PATCH /api/v1/pago/{idPago}/status/{status}

Colección Postman

He añadido una colección Postman con los cuatro endpoints listados anteriormente. Puedes importarla en Postman (o en otras herramientas compatibles) desde el archivo:

`postman/BancoBase.postman_collection.json`

Instrucciones para importar la colección en Postman:

1. Abrir Postman
2. Archivo -> Importar -> Seleccionar `postman/BancoBase.postman_collection.json`
3. La colección aparecerá como "Banco Base API" y podrás ejecutar las peticiones apuntando a `http://localhost:8081`.

5) Conexión a la base de datos

La aplicación está configurada para usar MySQL según `src/main/resources/application.properties`:

- URL JDBC: jdbc:mysql://localhost/testBase?createDatabaseIfNotExist=true
- Usuario: admin
- Contraseña: root

Si usas el contenedor Docker para MySQL, asegúrate de mapear puertos correctamente o ajustar la URL para apuntar al host/puerto del contenedor.

6) Topics de Kafka y URL de Kafka-UI

- Topic creado en la aplicación: `Banco_base_topic`
- Group ID usado por el consumer: `banco-base-group-id`

Kafka UI (interfaz web) si está habilitado en `docker-compose.yml`:

- URL: http://localhost:8080/

Comandos útiles para Kafka (desde PowerShell en el directorio con docker-compose):

```powershell  
# Listar topics  
docker-compose exec broker kafka-topics --bootstrap-server broker:29092 --list  
  
# Crear topic (ejemplo)  
docker-compose exec broker kafka-topics --bootstrap-server broker:29092 --create --topic Banco_base_topic --partitions 1 --replication-factor 1  
  
# Consumidor desde consola (dentro del contenedor broker)  
docker-compose exec broker kafka-console-consumer --bootstrap-server broker:29092 --topic Banco_base_topic --from-beginning  
  
# Productor de prueba desde consola (dentro del contenedor broker)  
docker-compose exec broker kafka-console-producer --broker-list broker:29092 --topic Banco_base_topic  
```

## Diagrama de secuencia: POST /api/v1/pagos

En el siguiente diagrama se muestra el flujo al crear un pago: la petición llega a `PagoController`, pasa a `PagoService`, se persiste con `PagoRepository` en la base de datos y, después, el servicio consulta/notifica el endpoint `/api/v1/pagos/{id}/status`.

![Diagrama de secuencia pagos](./docs/pagos-sequence-v2.png)

Descripción rápida:

- Cliente: origen de la petición (frontend, Postman, etc.).
- `PagoController` (`/api/v1/pagos`): recibe la solicitud y delega a la capa de servicio.
- `PagoService`: lógica de negocio; persiste la entidad y, tras confirmar la persistencia, realiza la llamada al endpoint de estado.
- `PagoRepository`: interfaz JPA que persiste la entidad en la base de datos.
- `Base de Datos`: almacena el pago y devuelve el ID generado.
- `StatusEndpoint` (`/api/v1/pagos/{id}/status`): endpoint consultado/llamado tras la persistencia para obtener o actualizar el estado del pago.

