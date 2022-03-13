package com.kevinchamorro.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.kevinchamorro.models.entities.ClienteEntity;
import com.kevinchamorro.models.wrappers.ClienteWrap;
import com.kevinchamorro.services.IClienteService;

@RestController
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<ClienteWrap>> getAll() {
		return new ResponseEntity<>(clienteService.getAll(),HttpStatus.OK);
	}
	
	@GetMapping
	public ResponseEntity<ClienteWrap> get(Long id) {
		return new ResponseEntity<>(clienteService.getById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ClienteEntity> post(@RequestBody ClienteWrap clienteWrap) {
		return new ResponseEntity<>(clienteService.create(clienteWrap),HttpStatus.CREATED);
	}
	
	@PutMapping
	public ResponseEntity<ClienteEntity> put(@RequestBody ClienteWrap clienteWrap) {
		return new ResponseEntity<>(clienteService.update(clienteWrap),HttpStatus.CREATED);
	}
	
	@DeleteMapping
	public ResponseEntity<String> post(Long id) {
		clienteService.delete(id);
		return new ResponseEntity<>("Eliminado correctamnete",HttpStatus.CREATED);
	}

}
