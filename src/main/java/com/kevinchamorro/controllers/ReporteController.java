package com.kevinchamorro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevinchamorro.models.wrappers.ReporteEstadoCuentaWrap;
import com.kevinchamorro.services.ICuentaService;

@RestController
@RequestMapping("reportes")
public class ReporteController {
	
	@Autowired
	private ICuentaService cuentaServie;
	
	@GetMapping("{codigoCliente}/{fechaInicio}/{fechaFin}")
	public ResponseEntity<List<ReporteEstadoCuentaWrap>> get(
			@PathVariable String codigoCliente, 
			@PathVariable String fechaInicio, 
			@PathVariable String fechaFin) {
		return new ResponseEntity<>(cuentaServie.generarReporteEstadoCuenta(codigoCliente, fechaInicio, fechaFin),HttpStatus.OK);
	}
	
}
