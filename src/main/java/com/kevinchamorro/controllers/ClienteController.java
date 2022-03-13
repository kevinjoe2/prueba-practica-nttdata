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

import com.kevinchamorro.models.entities.ClienteEntity;
import com.kevinchamorro.models.wrappers.ClienteWrap;
import com.kevinchamorro.services.IClienteService;

@RestController
@RequestMapping("clientes")
public class ClienteController {
	
	@Autowired
	private IClienteService clienteService;
	
	@GetMapping
	public ResponseEntity<List<ClienteEntity>> get() {
		return new ResponseEntity<>(clienteService.get(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<ClienteEntity> getById(@PathVariable Long id) {
		return new ResponseEntity<>(clienteService.getById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<ClienteEntity> post(@RequestBody ClienteWrap clienteWrap) throws Exception {
		return new ResponseEntity<>(clienteService.post(clienteWrap),HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<ClienteEntity> put(@PathVariable Long id, @RequestBody ClienteWrap clienteWrap) throws Exception {
		return new ResponseEntity<>(clienteService.put(id, clienteWrap),HttpStatus.CREATED);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<ClienteEntity> patch(@PathVariable Long id, @RequestBody ClienteWrap clienteWrap) throws Exception {
		return new ResponseEntity<>(clienteService.patch(id, clienteWrap),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
		clienteService.delete(id);
		return new ResponseEntity<>("Eliminado correctamnete",HttpStatus.GONE);
	}

}
