package com.bolsadeideas.springboot.backend.apirest.models.dao;


import org.springframework.data.repository.CrudRepository;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Sucursal;

public interface ISucursalDao extends CrudRepository<Sucursal, Long> {
}
