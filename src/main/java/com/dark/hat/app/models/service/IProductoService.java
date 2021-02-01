package com.dark.hat.app.models.service;

import java.util.List;
import com.dark.hat.app.models.entity.Producto;

public interface IProductoService {
	
	public List<Producto> findByNombre(String nombre);
	
	public Producto findProductoById(Long id);

}
