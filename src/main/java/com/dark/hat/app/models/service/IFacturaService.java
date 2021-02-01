package com.dark.hat.app.models.service;

import com.dark.hat.app.models.entity.Factura;

public interface IFacturaService {

	public void save(Factura factura);
	
	public Factura findFacturaById(Long id);
	
	public void delete(Long id);
}
