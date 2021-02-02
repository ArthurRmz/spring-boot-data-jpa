package com.dark.hat.app.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.dark.hat.app.models.entity.Cliente;
import com.dark.hat.app.models.service.IClienteService;
import com.dark.hat.app.models.service.IUploadFileService;
import com.dark.hat.app.util.paginator.PageRender;

import lombok.extern.log4j.Log4j2;

@Controller
@SessionAttributes("cliente")
@Log4j2
public class ClienteController {

	private static String URL_LISTAR = "/listar";

	private static String MENSAJE_FLASH_SUCCESS = "success";

	private static String MENSAJE_FLASH_INFO = "info";

	private static String MENSAJE_FLASH_ERROR = "error";

	private static String TITULO_PAGINA = "titulo";

	@Autowired
	// @Qualifier("clienteDaoJPA")
	private IClienteService clienteService;

	@Autowired
	private IUploadFileService uploadFileService;

	// {filename:.+} esa expresion permite que spring no borre la extension del
	// archivo
	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

		Resource recurso = null;
		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			log.error("Ocurrio un error: ".concat(e.toString()));
		}

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
				"attachment; filename=\"".concat(recurso.getFilename()).concat("\"")).body(recurso);

	}

	@GetMapping(value = "/ver/{id}")
	public String ver(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = clienteService.fetchByIdWithFacturas(id);
		if (cliente == null) {
			flash.addFlashAttribute(MENSAJE_FLASH_ERROR, "El cliente no existe en la base de datos");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put(TITULO_PAGINA, "Detalle cliente: ".concat(cliente.getNombre()));
		return "ver";
	}

	@RequestMapping(value = "/listar", method = RequestMethod.GET)
	public String listar(@RequestParam(name = "page", defaultValue = "0") int page, Model model) {
		Pageable pageRequest = PageRequest.of(page, 3);

		Page<Cliente> clientes = clienteService.findAll(pageRequest);
		PageRender<Cliente> pageRender = new PageRender<>(URL_LISTAR, clientes);
		model.addAttribute(TITULO_PAGINA, "Listado de clientes");
		model.addAttribute("clientes", clientes);
		model.addAttribute("page", pageRender);
		return "listar";
	}

	@RequestMapping(value = "/form", method = RequestMethod.GET)
	public String crear(Map<String, Object> model) {
		Cliente cliente = new Cliente();
		model.put("cliente", cliente);
		model.put(TITULO_PAGINA, "Formulario de cliente");
		return "form";
	}

	@RequestMapping(value = "/form", method = RequestMethod.POST)
	public String guardar(@Valid @ModelAttribute("cliente") Cliente cliente, BindingResult result, Model model,
			@RequestParam("file") MultipartFile foto, SessionStatus status, RedirectAttributes flash) {
		if (result.hasErrors()) {
			// El objeto "cliente" se pasa al objeto modelo porque se llama igual la clase
			// como el cliente
			// Si se deseara pasar con otro nombre eso se indica con la anotacion
			// @ModelAttribute
			model.addAttribute(TITULO_PAGINA, "Formulario de cliente");
			return "form";
		}

		if (!foto.isEmpty()) {

			if (cliente.getId() != null && cliente.getId() > 0 && cliente.getFoto() != null
					&& cliente.getFoto().length() > 0) {
				uploadFileService.delete(cliente.getFoto());
			}
			String uniqueFilename = null;
			try {
				uniqueFilename = uploadFileService.copy(foto);
			} catch (IOException e) {
				log.error("Ocurrio un error: ".concat(e.toString()));
			}
			flash.addFlashAttribute(MENSAJE_FLASH_INFO,
					"Has subido correctamente '".concat(uniqueFilename).concat("'"));
			cliente.setFoto(uniqueFilename);
		}

		String mensajeFlash = (cliente.getId()) != null ? "Cliente editado con exito" : "Cliente creado con éxito";

		clienteService.save(cliente);
		status.setComplete();
		flash.addFlashAttribute(MENSAJE_FLASH_SUCCESS, mensajeFlash);
		return "redirect:listar";
	}

	@RequestMapping(value = "/form/{id}", method = RequestMethod.GET)
	public String editar(@PathVariable(value = "id") Long id, Map<String, Object> model, RedirectAttributes flash) {
		Cliente cliente = null;
		if (id > 0) {
			cliente = clienteService.findOne(id);
			if (cliente == null) {
				flash.addFlashAttribute(MENSAJE_FLASH_ERROR, "El id del cliente no existe en la BD");
				return "redirect:/listar";
			}
		} else {
			flash.addFlashAttribute(MENSAJE_FLASH_ERROR, "El id del cliente no puede ser cero");
			return "redirect:/listar";
		}
		model.put("cliente", cliente);
		model.put(TITULO_PAGINA, "Editar cliente");
		return "form";
	}

	@RequestMapping(value = "/eliminar/{id}", method = RequestMethod.GET)
	public String editar(@PathVariable(value = "id") Long id, RedirectAttributes flash) {
		if (id > 0) {
			Cliente cliente = clienteService.findOne(id);

			clienteService.delete(id);
			flash.addFlashAttribute(MENSAJE_FLASH_SUCCESS, "Cliente eliminado con éxito");

			if (uploadFileService.delete(cliente.getFoto())) {
				flash.addFlashAttribute(MENSAJE_FLASH_INFO,
						"Foto ".concat(cliente.getFoto()).concat(" eliminada con exito!"));
			}

		}
		return "redirect:/listar";
	}
}
