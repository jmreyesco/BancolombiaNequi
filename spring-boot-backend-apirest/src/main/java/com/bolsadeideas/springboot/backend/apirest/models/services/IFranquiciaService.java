package com.bolsadeideas.springboot.backend.apirest.models.services;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Sucursal;

import reactor.core.publisher.Mono;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;

import java.util.List;
import java.util.Map;

public interface IFranquiciaService {
	
    List<Franquicia> findAll();
    Franquicia saveFranquicia(Franquicia franquicia);
    Mono<Franquicia> saveFranquiciaWeb(Franquicia franquicia);
    
    Franquicia findFranquiciaById(Long id);
    void deleteFranquicia(Long id);
    Franquicia addSucursal(Long franquiciaId, Sucursal sucursal);
    Mono<Franquicia> addSucursalWeb(Long franquiciaId, Sucursal sucursal);
    
    Sucursal addProducto(Long franquiciaId, Long sucursalId, Producto producto);
    
    void deleteProducto(Long franquiciaId, Long sucursalId, Long productoId);
    Producto updateStock(Long franquiciaId, Long sucursalId, Long productoId, int nuevoStock);
    List<Map<String, Object>> getProductoConMasStock(Long franquiciaId);
    
    //Plus
 // Métodos adicionales para los "plus"
    Franquicia updateNombreFranquicia(Long franquiciaId, String nuevoNombre);
    Sucursal updateNombreSucursal(Long franquiciaId, Long sucursalId, String nuevoNombre);
    Producto updateNombreProducto(Long franquiciaId, Long sucursalId, Long productoId, String nuevoNombre);
}

