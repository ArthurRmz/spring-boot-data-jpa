package com.dark.hat.app.controllers;

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.dark.hat.app.models.entity.Cliente;
import com.dark.hat.app.models.entity.Factura;
import com.dark.hat.app.models.entity.ItemFactura;
import com.dark.hat.app.models.entity.Producto;
import com.dark.hat.app.models.service.IClienteService;
import com.dark.hat.app.models.service.IFacturaService;
import com.dark.hat.app.models.service.IProductoService;

//@Secured("ROLE_ADMIN")
@PreAuthorize("hasRole('ROLE_ADMIN')")
@Controller
@RequestMapping("/factura")
@SessionAttributes("factura")
public class FacturaController {
	
	private static String TITULO = "titulo";

	private static String TITULO_CREAR_FACTURA = "Crear Factura";

	private static String VISTA_FACTURA_FORM = "factura/form";
	
	private static String OBJ_FACTURA = "factura";

	@Autowired
	private IClienteService clienteService;

	@Autowired
	private IProductoService productoService;
	
	@Autowired
	private IFacturaService facturaService;
	
	@GetMapping("/form/{clienteId}")
	public String crear(@PathVariable(value = "clienteId") Long clienteId, Model model, RedirectAttributes flash) {
		Cliente cliente = clienteService.findOne(clienteId);
		if(cliente==null) {
			flash.addFlashAttribute("error", "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		Factura factura = new Factura();
		factura.setCliente(cliente);
		model.addAttribute(OBJ_FACTURA, factura);
		model.addAttribute(TITULO,TITULO_CREAR_FACTURA);
		return VISTA_FACTURA_FORM;
	}
	
	@PostMapping("/form/")
	public String guardar(@Valid Factura factura,
			BindingResult result,
			Model model,
			@RequestParam(name = "item_id[]",required = false) Long[] itemIds,
			@RequestParam(name = "cantidad[]",required = false) Integer[] cantidades,
			RedirectAttributes flash,
			SessionStatus status) {
		
		if(result.hasErrors()) {
			model.addAttribute(TITULO, TITULO_CREAR_FACTURA);
			return VISTA_FACTURA_FORM;
		}
		
		if(itemIds==null || itemIds.length==0) {
			model.addAttribute(TITULO, TITULO_CREAR_FACTURA);
			model.addAttribute("error","Error: La factura no puede no tener lineas!");
			return VISTA_FACTURA_FORM;
		}
		
		for (int i = 0; i < itemIds.length; i++) {
			Producto producto = productoService.findProductoById(itemIds[i]);
			ItemFactura linea = new ItemFactura();
			linea.setProducto(producto);
			linea.setCantidad(cantidades[i]);
			factura.agregarItemFactura(linea);
			/**log.info("Id: "
					.concat(itemIds[i].toString())
					.concat(", cantidad: ")
					.concat(cantidades[i].toString()));*/
		}
		
		facturaService.save(factura);
		status.setComplete();
		flash.addFlashAttribute("success","Factura creada con exito!");
		return "redirect:/ver/".concat(factura.getCliente().getId().toString());
		
	}
	
	@GetMapping("/ver/{id}")
	public String ver (@PathVariable(value="id") Long id,
			Model model,
			RedirectAttributes flash) {
		Factura factura = facturaService.fetchFacturaByIdWithClienteWithItemFacturaWithProducto(id);
		//Factura factura = facturaService.findFacturaById(id);
		if(factura==null) {
			flash.addFlashAttribute("error", "La factura no existe en la base de datos!");
			return "redirect:/listar";
		}
		model.addAttribute(OBJ_FACTURA, factura);
		model.addAttribute(TITULO,"Factura: ".concat(factura.getDescripcion()));
		return "factura/ver";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		Factura factura = facturaService.findFacturaById(id);
		if(factura!=null) {
			facturaService.delete(id);
			flash.addFlashAttribute("success","Factura eliminada con exito!");
			return "redirect:/ver/".concat(factura.getCliente().getId().toString());
		}
		flash.addFlashAttribute("error","La factura no existe ena la base de datos, no se pudo eliminar!");
		return "redirect:/listar";
	}

}
