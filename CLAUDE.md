# taller4-spring-docker

Ecosistema de microservicios Spring Boot 3.5.16 / Spring Cloud 2025.0.3 (Java 21, Maven) empaquetado con Docker Compose. Taller 4 de Electiva I, basado en la *Guía de implementación: proyectos Spring Boot con Docker*.

## Comandos verificados

```bash
cp .env.example .env                 # una sola vez; cambiar DB_PASSWORD
docker compose up -d --build         # construye y levanta todo (8 contenedores)
docker compose ps                    # esperar a que todos estén "healthy"
docker compose config                # valida el YAML con variables resueltas
docker compose down                  # conserva volúmenes; "down -v" borra los datos
cd producto-service && mvn clean package   # compila y corre los tests de un servicio (idem los demás)
```

No hay `pom.xml` padre: cada servicio es un proyecto Maven independiente y su `Dockerfile` usa como contexto su propia carpeta. El Dockerfile compila con `-DskipTests`; los tests se corren con `mvn package` en local.

## Arquitectura

| Servicio | Puerto | Publicado | Rol |
|---|---|---|---|
| `config-server` | 8888 | No | Config nativa desde `src/main/resources/configuracion/` (`application.yml` compartido + uno por servicio) |
| `eureka-server` | 8761 | Sí (panel) | Descubrimiento |
| `api-gateway` | 8080 | Sí | Entrada única; rutas `lb://` en su `application.yml` (`spring.cloud.gateway.server.webflux.routes`) |
| `producto-service` | 8081 | No | CRUD de productos, 2 réplicas, DB `productos` |
| `pedido-service` | 8082 | No | Pedidos; llama a `http://producto-service` con `RestClient` `@LoadBalanced`, DB `pedidos` |

Los servicios de negocio siguen `controlador → servicio → repositorio` con DTO `record` (nunca entidades JPA hacia la API) y nombres en español. Todos usan `spring.config.import: optional:configserver:${CONFIG_SERVER_URL:...}` y `${EUREKA_URL:...}`, de modo que el mismo JAR corre en local y en Docker.

## Trampas conocidas

- **503 del gateway justo tras levantar:** Eureka tarda hasta ~30 s en propagar el registro; reintentar. No es un fallo.
- **`producto-service` no publica puertos** porque tiene 2 réplicas; un puerto fijo en el host chocaría. En equipos de 8 GB bajar a `replicas: 1`.
- **Healthchecks con `wget -qO-`:** la imagen `eclipse-temurin:21-jre-alpine` no trae `curl`.
- **Dockerfile en 3 etapas** (compilar → extraer capas con `jarmode=tools` → imagen final con usuario `spring`). La imagen base lleva tag fijo; nunca `latest`.
- El `.env` real nunca se versiona; solo `.env.example`. Las credenciales llegan a los servicios por variables de entorno.
- Carrera posible al crear tablas con dos réplicas sobre una DB vacía (`ddl-auto: update`); `restart: on-failure` la absorbe.

## Documentación y evidencias

- `README.md`: arquitectura, despliegue, uso, cumplimiento de la lista de chequeo.
- `docs/evidencias/`: capturas de ejecución (índice en su `README.md`). Si cambia el comportamiento, volver a capturar y actualizar el índice.
- `postman/ecommerce-docker.postman_collection.json`: 14 peticiones contra el gateway; ejecutarlas en orden.

## Git

Commits Conventional en español (`tipo(scope): descripción`), directo a `main`, sin ramas ni PR, sin atribución de IA. Ver las reglas globales del usuario.
