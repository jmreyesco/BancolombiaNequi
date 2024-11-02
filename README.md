# BancolombiaNequi
Prueba Developer



# Proyecto API de Franquicias - Spring Boot + WebFlux

Este proyecto es una API REST para la gestión de franquicias, sucursales y productos, construida con Spring Boot y con soporte de operaciones reactivas a través de WebFlux.

## Descripción

La API permite:
- Crear y gestionar franquicias.
	Método: POST
URL: http://localhost:8090/api (asegúrate de que el puerto sea el correcto)

- Agregar sucursales a una franquicia.
	Método: POST
URL: http://localhost:8090/api/{id}/sucursales (reemplaza {id} con el ID de la franquicia)
	
- Añadir productos a las sucursales.
Método: POST
URL: http://localhost:8090/api/{idFranquicia}/sucursales/{idSucursal}/productos (reemplaza {idFranquicia} y {idSucursal} con los IDs correspondientes)

-Eliminar Productos
Método: DELETE
URL: http://localhost:8090/api/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto} (reemplaza con los IDs correspondientes)

- Actualizar el stock de los productos.
Método: PUT
URL: http://localhost:8980/api/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}/stock (reemplaza con los IDs correspondientes)

- Obtener el producto con mayor stock en una franquicia.
	Método: GET
URL: http://localhost:8090/api/{id}/producto-max-stock (reemplaza {id} con el ID de la franquicia)

	
  
Además, incluye funciones adicionales (endpoints "plus") para actualizar los nombres de franquicias, sucursales con WebFlux.
http://localhost:8090/api/webflux
http://localhost:8090/api/webflux/1/sucursales

## Requisitos previos

Asegúrate de tener instalado:
- **Java 1.8**
- **Maven o Gradle**
- **Postgres** (`application.properties`)


### Clonar el Repositorio

```bash
git clone <URL-del-repositorio>
cd <nombre-del-proyecto>

