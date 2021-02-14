package com.dark.hat.app.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Controller
public class ViewController {
	@GetMapping(value = "/view")
	public String view(@RequestParam(name = "format") String format, HttpServletRequest request) {
		log.info("[ViewController][Inicio]");
		String ultimaUrl = request.getHeader("referer");
		log.info("[ViewController][url][".concat(ultimaUrl).concat("]"));
		return "redirect:".concat(ultimaUrl).concat("&").concat("format=").concat(format);
	}

}
