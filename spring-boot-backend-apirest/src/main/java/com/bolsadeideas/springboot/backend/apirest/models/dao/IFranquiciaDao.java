package com.bolsadeideas.springboot.backend.apirest.models.dao;

import org.springframework.data.repository.CrudRepository;
import com.bolsadeideas.springboot.backend.apirest.models.entity.Franquicia;

public interface IFranquiciaDao extends CrudRepository<Franquicia, Long> {
}
