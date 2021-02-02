package com.dark.hat.app.models.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.dark.hat.app.models.dao.IFacturaDao;
import com.dark.hat.app.models.entity.Factura;

@Service
public class FacturaServiceImpl implements IFacturaService{

	@Autowired
	private IFacturaDao facturaDao;
	
	@Override
	@Transactional
	public void save(Factura factura) {
		facturaDao.save(factura);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura findFacturaById(Long id) {
		return facturaDao.findById(id).orElse(null);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		facturaDao.deleteById(id);
	}

	@Override
	@Transactional(readOnly = true)
	public Factura fetchFacturaByIdWithClienteWithItemFacturaWithProducto(Long Id) {
		return facturaDao.fetchByIdWithClienteWithItemFacturaWithProducto(Id);
	}

}
