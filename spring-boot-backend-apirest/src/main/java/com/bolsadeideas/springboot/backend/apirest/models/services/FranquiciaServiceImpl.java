package com.bolsadeideas.springboot.backend.apirest.models.services;

import com.bolsadeideas.springboot.backend.apirest.models.dao.IFranquiciaDao;
import com.bolsadeideas.springboot.backend.apirest.models.dao.ISucursalDao;
import com.bolsadeideas.springboot.backend.apirest.models.dao.IProductoDao;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Sucursal;

import reactor.core.publisher.Mono;

import com.bolsadeideas.springboot.backend.apirest.models.entity.Producto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.HashMap;
import java.util.ArrayList;

@Service
public class FranquiciaServiceImpl implements IFranquiciaService {

    @Autowired
    private IFranquiciaDao franquiciaDao;

    @Autowired
    private ISucursalDao sucursalDao;

    @Autowired
    private IProductoDao productoDao;

    @Override
    @Transactional(readOnly = true)
    public List<Franquicia> findAll() {
        return (List<Franquicia>) franquiciaDao.findAll();
    }

    @Override
    @Transactional
    public Franquicia saveFranquicia(Franquicia franquicia) {
        return franquiciaDao.save(franquicia);
    }

    @Override
    @Transactional(readOnly = true)
    public Franquicia findFranquiciaById(Long id) {
        return franquiciaDao.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void deleteFranquicia(Long id) {
        franquiciaDao.deleteById(id);
    }

    @Override
    @Transactional
    public Franquicia addSucursal(Long franquiciaId, Sucursal sucursal) {
        Franquicia franquicia = franquiciaDao.findById(franquiciaId).orElse(null);
        if (franquicia != null) {
            franquicia.getSucursales().add(sucursal);
            sucursal.setFranquicia(franquicia);
            sucursalDao.save(sucursal);
            franquiciaDao.save(franquicia);
        }
        return franquicia;
    }

    @Override
    @Transactional
    public Sucursal addProducto(Long franquiciaId, Long sucursalId, Producto producto) {
        Sucursal sucursal = sucursalDao.findById(sucursalId).orElse(null);
        if (sucursal != null && sucursal.getFranquicia().getId().equals(franquiciaId)) {
            sucursal.getProductos().add(producto);
            producto.setSucursal(sucursal);
            productoDao.save(producto);
            sucursalDao.save(sucursal);
        }
        return sucursal;
    }

    @Override
    @Transactional
    public void deleteProducto(Long franquiciaId, Long sucursalId, Long productoId) {
        Optional<Producto> productoOpt = productoDao.findById(productoId);
        if (productoOpt.isPresent()) {
            Producto producto = productoOpt.get();
            if (producto.getSucursal().getId().equals(sucursalId) && producto.getSucursal().getFranquicia().getId().equals(franquiciaId)) {
                productoDao.delete(producto);
            }
        }
    }

    @Override
    @Transactional
    public Producto updateStock(Long franquiciaId, Long sucursalId, Long productoId, int nuevoStock) {
        Producto producto = productoDao.findById(productoId).orElse(null);
        if (producto != null && producto.getSucursal().getId().equals(sucursalId) && producto.getSucursal().getFranquicia().getId().equals(franquiciaId)) {
            producto.setStock(nuevoStock);
            productoDao.save(producto);
        }
        return producto;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getProductoConMasStock(Long franquiciaId) {
        List<Map<String, Object>> productosConMasStock = new ArrayList<>();
        Franquicia franquicia = franquiciaDao.findById(franquiciaId).orElse(null);
        
        if (franquicia != null) {
            franquicia.getSucursales().forEach(sucursal -> {
                Producto productoMaxStock = sucursal.getProductos().stream().max((p1, p2) -> Integer.compare(p1.getStock(), p2.getStock())).orElse(null);
                
                if (productoMaxStock != null) {
                    Map<String, Object> productoInfo = new HashMap<>();
                    productoInfo.put("sucursal", sucursal.getNombre());
                    productoInfo.put("producto", productoMaxStock.getNombre());
                    productoInfo.put("stock", productoMaxStock.getStock());
                    productosConMasStock.add(productoInfo);
                }
            });
        }
        
        return productosConMasStock;
    }
    
    
    //Plus
    @Override
    @Transactional
    public Franquicia updateNombreFranquicia(Long franquiciaId, String nuevoNombre) {
        Franquicia franquicia = franquiciaDao.findById(franquiciaId).orElse(null);
        if (franquicia != null) {
            franquicia.setNombre(nuevoNombre);
            franquiciaDao.save(franquicia);
        }
        return franquicia;
    }

    @Override
    @Transactional
    public Sucursal updateNombreSucursal(Long franquiciaId, Long sucursalId, String nuevoNombre) {
        Sucursal sucursal = sucursalDao.findById(sucursalId).orElse(null);
        if (sucursal != null && sucursal.getFranquicia().getId().equals(franquiciaId)) {
            sucursal.setNombre(nuevoNombre);
            sucursalDao.save(sucursal);
        }
        return sucursal;
    }
    
    @Override
    @Transactional
    public Producto updateNombreProducto(Long franquiciaId, Long sucursalId, Long productoId, String nuevoNombre) {
        Producto producto = productoDao.findById(productoId).orElse(null);
        if (producto != null && producto.getSucursal().getId().equals(sucursalId) && producto.getSucursal().getFranquicia().getId().equals(franquiciaId)) {
            producto.setNombre(nuevoNombre);
            productoDao.save(producto);
        }
        return producto;
    }
    
    //------------------------------WEBFLUX-----------------------------------------------
    @Override
    @Transactional
    public Mono<Franquicia> saveFranquiciaWeb(Franquicia franquicia) {
    	return Mono.fromCallable(() -> franquiciaDao.save(franquicia));
    }
    
    @Override
    @Transactional
    public Mono<Franquicia> addSucursalWeb(Long franquiciaId, Sucursal sucursal) {
        return Mono.fromCallable(() -> {
            Franquicia franquicia = franquiciaDao.findById(franquiciaId).orElse(null);
            if (franquicia != null) {
                franquicia.getSucursales().add(sucursal);
                sucursal.setFranquicia(franquicia);
                sucursalDao.save(sucursal);
                franquiciaDao.save(franquicia);
            }
            return franquicia;
        });
    }



}
