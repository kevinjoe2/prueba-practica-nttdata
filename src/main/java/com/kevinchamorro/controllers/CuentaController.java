package com.kevinchamorro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kevinchamorro.models.entities.CuentaEntity;
import com.kevinchamorro.models.wrappers.CuentaWrap;
import com.kevinchamorro.services.ICuentaService;

@RestController
@RequestMapping("cuentas")
public class CuentaController {
	
	@Autowired
	private ICuentaService cuentaServie;
	
	@GetMapping
	public ResponseEntity<List<CuentaEntity>> get() {
		return new ResponseEntity<>(cuentaServie.get(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<CuentaEntity> getById(@PathVariable Long id) {
		return new ResponseEntity<>(cuentaServie.getById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<CuentaEntity> post(@RequestBody CuentaWrap cuentaWrap) throws Exception {
		return new ResponseEntity<>(cuentaServie.post(cuentaWrap),HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<CuentaEntity> put(@PathVariable Long id, @RequestBody CuentaWrap cuentaWrap) throws Exception {
		return new ResponseEntity<>(cuentaServie.put(id, cuentaWrap),HttpStatus.CREATED);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<CuentaEntity> patch(@PathVariable Long id, @RequestBody CuentaWrap cuentaWrap) throws Exception {
		return new ResponseEntity<>(cuentaServie.patch(id, cuentaWrap),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
		cuentaServie.delete(id);
		return new ResponseEntity<>("Eliminado correctamnete",HttpStatus.GONE);
	}
	
}
