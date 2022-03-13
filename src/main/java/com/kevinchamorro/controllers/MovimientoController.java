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

import com.kevinchamorro.models.entities.MovimientoEntity;
import com.kevinchamorro.models.wrappers.MovimientoWrap;
import com.kevinchamorro.services.IMovimientoService;

@RestController
@RequestMapping("movimientos")
public class MovimientoController {

	@Autowired
	private IMovimientoService movimientoService;
	
	@GetMapping
	public ResponseEntity<List<MovimientoEntity>> get() {
		return new ResponseEntity<>(movimientoService.get(),HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<MovimientoEntity> getById(@PathVariable Long id) {
		return new ResponseEntity<>(movimientoService.getById(id),HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<MovimientoEntity> post(@RequestBody MovimientoWrap movimientoWrap) throws Exception {
		return new ResponseEntity<>(movimientoService.post(movimientoWrap),HttpStatus.CREATED);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<MovimientoEntity> put(@PathVariable Long id, @RequestBody MovimientoWrap movimientoWrap) throws Exception {
		return new ResponseEntity<>(movimientoService.put(id, movimientoWrap),HttpStatus.CREATED);
	}
	
	@PatchMapping("{id}")
	public ResponseEntity<MovimientoEntity> patch(@PathVariable Long id, @RequestBody MovimientoWrap movimientoWrap) throws Exception {
		return new ResponseEntity<>(movimientoService.patch(id, movimientoWrap),HttpStatus.CREATED);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<String> delete(@PathVariable Long id) throws Exception {
		movimientoService.delete(id);
		return new ResponseEntity<>("Eliminado correctamnete",HttpStatus.GONE);
	}
	
}
