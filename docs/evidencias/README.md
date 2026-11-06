# Evidencias de ejecución

Capturas tomadas de la ejecución real del proyecto (Docker Desktop, Windows 11, 6-nov-2026).

| Archivo | Qué demuestra |
|---|---|
| `01-docker-compose-ps.png` | `docker compose ps`: los 8 contenedores en `healthy`; solo el gateway (8080) y el panel de Eureka (8761) publican puertos |
| `02-imagenes-y-capas.png` | `docker images` y `docker history`: imágenes con tag fijo `1.0`, capas separadas (dependencias → loader → snapshot → application) y `USER spring` |
| `03-usuario-no-root.png` | Los contenedores corren con el usuario `spring` (uid 100), no como root |
| `04-docker-stats.png` | Consumo real de memoria con el límite de 512 MB por servicio |
| `05-pruebas-gateway.png` | Pruebas contra el gateway: salud, alta de producto, listado, pedido (201), stock insuficiente (409) y producto inexistente (404) |
| `06-balanceo-replicas.png` | Las dos réplicas de `producto-service` registradas en Eureka y las peticiones alternándose entre ellas |
| `07-volumenes.png` | Volúmenes con nombre `pg-productos` y `pg-pedidos` |
| `08-persistencia-volumen.png` | `docker compose down` + `up -d`: los productos siguen ahí gracias al volumen |
| `09-eureka-dashboard.png` | Panel de Eureka con `API-GATEWAY`, `PEDIDO-SERVICE` y `PRODUCTO-SERVICE (2)` en `UP` |
| `10-seguridad-env.png` | `.env` ignorado por git y no versionado; solo `.env.example` está en el repositorio |
| `11-github-repositorio.png` | Repositorio publicado en GitHub |
