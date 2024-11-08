package com.bolsadeideas.springboot.backend.apirest.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Cliente;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Sucursal;
import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;
import com.bolsadeideas.springboot.backend.apirest.models.services.IFranquiciaService;

import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;

//Manage errors
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.slf4j.LoggerFactory;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api")
public class ClienteRestController {

	// Proyecto Bancolombia/Nequi

	@Autowired
	private IFranquiciaService franquiciaService;

	//Crear Franquicia
	@PostMapping
	public ResponseEntity<Franquicia> crearFranquicia(@RequestBody Franquicia nuevaFranquicia) {
		Franquicia franquiciaGuardada = franquiciaService.saveFranquicia(nuevaFranquicia);
		return ResponseEntity.status(HttpStatus.CREATED).body(franquiciaGuardada);
	}
	
	//WEBFLUX
	@PostMapping("/webflux")
	public Mono<ResponseEntity<Franquicia>> crearFranquiciaWeb(@RequestBody Franquicia nuevaFranquicia) {
	    return franquiciaService.saveFranquiciaWeb(nuevaFranquicia)
	            .map(franquicia -> ResponseEntity.status(HttpStatus.CREATED).body(franquicia));
	}


	//Agregar una Sucursal a una Franquicia
	@PostMapping("/{id}/sucursales")
	public ResponseEntity<Franquicia> agregarSucursal(@PathVariable Long id, @RequestBody Sucursal nuevaSucursal) {
	    Franquicia franquiciaActualizada = franquiciaService.addSucursal(id, nuevaSucursal);
	    return franquiciaActualizada != null ? ResponseEntity.status(HttpStatus.CREATED).body(franquiciaActualizada)
	            : ResponseEntity.notFound().build();
	}
	
	//-------------------WEBFLUX-------------------------------
	@PostMapping("/webflux/{id}/sucursales")
	public Mono<ResponseEntity<Franquicia>> agregarSucursalWeb(@PathVariable Long id, @RequestBody Sucursal nuevaSucursal) {
	    return franquiciaService.addSucursalWeb(id, nuevaSucursal)
	            .map(franquicia -> ResponseEntity.status(HttpStatus.CREATED).body(franquicia))
	            .defaultIfEmpty(ResponseEntity.notFound().build());
	}


	@PostMapping("/{idFranquicia}/sucursales/{idSucursal}/productos")
	public ResponseEntity<Sucursal> agregarProducto(@PathVariable Long idFranquicia, @PathVariable Long idSucursal,
			@RequestBody Producto nuevoProducto) {
		Sucursal sucursalActualizada = franquiciaService.addProducto(idFranquicia, idSucursal, nuevoProducto);
		return sucursalActualizada != null ? ResponseEntity.ok(sucursalActualizada) : ResponseEntity.notFound().build();
	}

	@DeleteMapping("/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}")
	public ResponseEntity<Void> eliminarProducto(@PathVariable Long idFranquicia, @PathVariable Long idSucursal,
			@PathVariable Long idProducto) {
		franquiciaService.deleteProducto(idFranquicia, idSucursal, idProducto);
		return ResponseEntity.noContent().build();
	}

	@PutMapping("/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}/stock")
	public ResponseEntity<Producto> actualizarStockProducto(@PathVariable Long idFranquicia,
			@PathVariable Long idSucursal, @PathVariable Long idProducto, @RequestBody int nuevoStock) {
		Producto productoActualizado = franquiciaService.updateStock(idFranquicia, idSucursal, idProducto, nuevoStock);
		return productoActualizado != null ? ResponseEntity.ok(productoActualizado) : ResponseEntity.notFound().build();
	}

	@GetMapping("/{id}/producto-max-stock")
	public ResponseEntity<List<Map<String, Object>>> productoConMasStock(@PathVariable Long id) {
		List<Map<String, Object>> productos = franquiciaService.getProductoConMasStock(id);
		return ResponseEntity.ok(productos);
	}
	
	
	//Plus------------------------------------------------------------------
	//Endpoint para actualizar el nombre de una franquicia
	
	// Endpoints "Plus"

    /**
     * Actualizar el nombre de una Franquicia
     */
    @PutMapping("/franquicias/{id}/nombre")
    public ResponseEntity<Franquicia> actualizarNombreFranquicia(@PathVariable Long id, @RequestBody String nuevoNombre) {
        Franquicia franquiciaActualizada = franquiciaService.updateNombreFranquicia(id, nuevoNombre);
        return franquiciaActualizada != null ? ResponseEntity.ok(franquiciaActualizada) : ResponseEntity.notFound().build();
    }

    /**
     * Actualizar el nombre de una Sucursal
     */
    @PutMapping("/franquicias/{idFranquicia}/sucursales/{idSucursal}/nombre")
    public ResponseEntity<Sucursal> actualizarNombreSucursal(@PathVariable Long idFranquicia, @PathVariable Long idSucursal, @RequestBody String nuevoNombre) {
        Sucursal sucursalActualizada = franquiciaService.updateNombreSucursal(idFranquicia, idSucursal, nuevoNombre);
        return sucursalActualizada != null ? ResponseEntity.ok(sucursalActualizada) : ResponseEntity.notFound().build();
    }

    /**
     * Actualizar el nombre de un Producto
     */
    @PutMapping("/franquicias/{idFranquicia}/sucursales/{idSucursal}/productos/{idProducto}/nombre")
    public ResponseEntity<Producto> actualizarNombreProducto(@PathVariable Long idFranquicia, @PathVariable Long idSucursal, @PathVariable Long idProducto, @RequestBody String nuevoNombre) {
        Producto productoActualizado = franquiciaService.updateNombreProducto(idFranquicia, idSucursal, idProducto, nuevoNombre);
        return productoActualizado != null ? ResponseEntity.ok(productoActualizado) : ResponseEntity.notFound().build();
    }

    
    //Metodos Reactivos



	// ----------------------- Bancolombia/Nequi ----------------------------

	// cambio
	public ClienteRestController(IClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@Autowired
	private IClienteService clienteService;

	private final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@GetMapping("/clientes")
	public ResponseEntity<List<Cliente>> index() {
		try {
			// return clienteService.findAll();
			log.info("Ingreso a metodo index");
			List<Cliente> clientes = clienteService.findAll();
			return ResponseEntity.ok(clientes); // 200 OK
		} catch (Exception e) {
			// TODO: handle exception
			// Captura y maneja cualquier excepción inesperada
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // 500 Internal Server Error
		}

	}

	@GetMapping("/clientes/{id}")
	public Cliente show(@PathVariable Long id) {
		return this.clienteService.findById(id);
	}

	@PostMapping("/clientes")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente create(@RequestBody Cliente cliente) {
		cliente.setCreateAt(new Date());
		this.clienteService.save(cliente);
		return cliente;
	}

	@PutMapping("/clientes/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public Cliente update(@RequestBody Cliente cliente, @PathVariable Long id) {
		Cliente currentCliente = this.clienteService.findById(id);
		currentCliente.setNombre(cliente.getNombre());
		currentCliente.setApellido(cliente.getApellido());
		currentCliente.setEmail(cliente.getEmail());
		this.clienteService.save(currentCliente);
		return currentCliente;
	}

	/*
	 * @DeleteMapping("/clientes/{id}")
	 * 
	 * @ResponseStatus(HttpStatus.NO_CONTENT) public void delete(@PathVariable Long
	 * id) { Cliente currentCliente = this.clienteService.findById(id);
	 * this.clienteService.delete(currentCliente); }
	 */

	@DeleteMapping("/clientes/{id}")
	public ResponseEntity<Map<String, Object>> delete(@PathVariable Long id) {

		Map<String, Object> response = new HashMap<>();
		Cliente currentCliente = this.clienteService.findById(id);

		if (currentCliente == null) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 Not Found
		}

		this.clienteService.delete(currentCliente);
		response.put("mensaje", "El cliente eliminado con éxito!");
		return ResponseEntity.status(HttpStatus.OK).body(response); // 200 OK
	}

}
