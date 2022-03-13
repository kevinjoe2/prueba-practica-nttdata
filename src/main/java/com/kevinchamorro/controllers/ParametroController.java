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

import com.kevinchamorro.models.entities.ParametroEntity;
import com.kevinchamorro.models.wrappers.ParametroWrap;
import com.kevinchamorro.services.IParametroService;

@RestController
@RequestMapping("parametros")
public class ParametroController {
	
	@Autowired
	private IParametroService parametroService;
	
	@GetMapping
	public ResponseEntity<List<ParametroEntity>> get() {
		return new ResponseEntity<>(parametroService.get(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ParametroEntity> getById(@PathVariable Long id) {
		return new ResponseEntity<>(parametroService.getById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ParametroEntity> post(@RequestBody ParametroWrap parametroWrap) throws Exception {
		return new ResponseEntity<>(parametroService.post(parametroWrap),HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ParametroEntity> put(@PathVariable Long id, @RequestBody ParametroWrap parametroWrap) throws Exception {
		return new ResponseEntity<>(parametroService.put(id, parametroWrap),HttpStatus.CREATED);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<ParametroEntity> patch(@PathVariable Long id, @RequestBody ParametroWrap parametroWrap) throws Exception {
		return new ResponseEntity<>(parametroService.patch(id, parametroWrap),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
		parametroService.delete(id);
		return new ResponseEntity<>("Eliminado correctamnete",HttpStatus.GONE);
	}

}
