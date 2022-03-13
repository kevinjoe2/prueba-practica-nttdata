package com.kevinchamorro.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kevinchamorro.models.entities.ClienteEntity;
import com.kevinchamorro.models.entities.PersonaEntity;
import com.kevinchamorro.models.wrappers.ClienteWrap;
import com.kevinchamorro.repositories.ClienteRepo;
import com.kevinchamorro.repositories.PersonaRepo;
import com.kevinchamorro.repositories.specs.PersonaSpecs;

@Service
public class ClienteServiceImp implements IClienteService {
	
	@Autowired
	private ClienteRepo clienteRepo;
	
	@Autowired
	private PersonaRepo personaRepo;

	@Override
	@Transactional(readOnly = true)
	public List<ClienteWrap> getAll() {
		
		List<ClienteEntity> clientes = (List<ClienteEntity>)clienteRepo.findAll();
		return clientes.stream()
				.map(m -> new ClienteWrap(
						m.getPersona().getIdentificacion(),
						m.getPersona().getNombre(),
						m.getPersona().getGenero(),
						m.getPersona().getFechaNacimiento().toString(),
						m.getPersona().getTelefono(),
						m.getPersona().getDireccion(),
						null))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteWrap getById(Long id) {
		
		ClienteEntity cliente = clienteRepo.findById(id).get();
		
		ClienteWrap clienteWrap = new ClienteWrap(
				cliente.getPersona().getIdentificacion(),
				cliente.getPersona().getNombre(),
				cliente.getPersona().getGenero(),
				cliente.getPersona().getFechaNacimiento().toString(),
				cliente.getPersona().getTelefono(),
				cliente.getPersona().getDireccion(),
				cliente.getClave());
		
		return clienteWrap;
	}
	
	@Override
	@Transactional()
	public ClienteEntity create(ClienteWrap nuevoCliente) {
		
		PersonaEntity persona = new PersonaEntity();
		persona.setIdentificacion(nuevoCliente.identificacion);
		persona.setNombre(nuevoCliente.nombres);
		persona.setGenero(nuevoCliente.genero);
		persona.setFechaNacimiento(LocalDate.parse(nuevoCliente.fechaNacimiento));
		persona.setDireccion(nuevoCliente.direccion);
		persona.setTelefono(nuevoCliente.telefono);
		persona.setEstatus(true);
		persona.setFechaCreacion(LocalDateTime.now());
		persona.setFechaActualizacion(LocalDateTime.now());
		
		ClienteEntity cliente = new ClienteEntity();
		cliente.setCodigoCliente("CLI0001");
		cliente.setClave(nuevoCliente.clave);
		cliente.setEstatus(true);
		cliente.setFechaCreacion(LocalDateTime.now());
		cliente.setFechaActualizacion(LocalDateTime.now());
		cliente.setPersona(persona);
		
		personaRepo.save(persona);
		clienteRepo.save(cliente);
		
		return cliente;
		
	}

	@Override
	@Transactional()
	public ClienteEntity update(ClienteWrap clienteWra) {
		
		PersonaEntity persona = personaRepo.findOne(PersonaSpecs.findByIdentificacion(clienteWra.identificacion)).orElse(null);
		
		persona.setDireccion(clienteWra.direccion);
		persona.setFechaActualizacion(LocalDateTime.now());
		persona.setFechaNacimiento(LocalDate.parse(clienteWra.fechaNacimiento));
		persona.setGenero(clienteWra.genero);
		persona.setNombre(clienteWra.nombres);
		persona.setTelefono(clienteWra.telefono);
		
		ClienteEntity cliente = clienteRepo.findById(persona.getId()).get();
		
		cliente.setClave(clienteWra.clave);
		cliente.setFechaActualizacion(LocalDateTime.now());
		
		personaRepo.save(persona);
		clienteRepo.save(cliente);
		
		return cliente;
	}

	@Override
	@Transactional()
	public void delete(Long id) {
		
		ClienteEntity cliente = clienteRepo.findById(id).get();
		PersonaEntity persona = personaRepo.findById(cliente.getPersona().getId()).get();
		
		clienteRepo.delete(cliente);
		personaRepo.delete(persona);
		
	}

}
