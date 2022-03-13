package com.kevinchamorro.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kevinchamorro.models.entities.ClienteEntity;
import com.kevinchamorro.models.entities.ParametroEntity;
import com.kevinchamorro.models.entities.PersonaEntity;
import com.kevinchamorro.models.wrappers.ClienteWrap;
import com.kevinchamorro.repositories.ClienteRepo;
import com.kevinchamorro.repositories.ParametroRepo;
import com.kevinchamorro.repositories.PersonaRepo;
import com.kevinchamorro.repositories.specs.ParametroSpecs;
import com.kevinchamorro.repositories.specs.PersonaSpecs;
import com.kevinchamorro.util.enums.CodigoParametroEnum;
import com.kevinchamorro.util.enums.GeneroEnum;
import com.kevinchamorro.util.func.UtilFunc;

@Service
public class ClienteServiceImp implements IClienteService {
	
	@Autowired
	private ClienteRepo clienteRepo;
	
	@Autowired
	private PersonaRepo personaRepo;
	
	@Autowired
	private ParametroRepo parametroRepo;

	@Override
	@Transactional(readOnly = true)
	public List<ClienteEntity> get() {
		
		return (List<ClienteEntity>)clienteRepo.findAll();
	}

	@Override
	@Transactional(readOnly = true)
	public ClienteEntity getById(Long id) {
		
		ClienteEntity cliente = clienteRepo.findById(id).orElse(null);
		
		return cliente;
	}
	
	@Override
	@Transactional()
	public ClienteEntity post(ClienteWrap clienteWrap) throws Exception {
		
		PersonaEntity persona = new PersonaEntity();
		persona.setIdentificacion(clienteWrap.identificacion);
		persona.setNombre(clienteWrap.nombres);
		persona.setGenero(UtilFunc.getEnumFromString(GeneroEnum.class, clienteWrap.genero));
		persona.setFechaNacimiento(LocalDate.parse(clienteWrap.fechaNacimiento));
		persona.setDireccion(clienteWrap.direccion);
		persona.setTelefono(clienteWrap.telefono);
		persona.setEstatus(clienteWrap.estatus);
		persona.setFechaCreacion(LocalDateTime.now());
		persona.setFechaActualizacion(LocalDateTime.now());
		
		ClienteEntity cliente = new ClienteEntity();
		cliente.setCodigoCliente(generarCodigoCliente());
		cliente.setClave(clienteWrap.clave);
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
	public ClienteEntity put(Long id, ClienteWrap clienteWrap) throws Exception {
		
		PersonaEntity persona = personaRepo.findOne(PersonaSpecs.findByIdentificacion(clienteWrap.identificacion)).orElse(null);
		
		if ( persona == null  ) {
			
			persona = new PersonaEntity();
			persona.setIdentificacion(clienteWrap.identificacion);
			persona.setFechaCreacion(LocalDateTime.now());
			
		}
		
		persona.setEstatus(clienteWrap.estatus);
		persona.setDireccion(clienteWrap.direccion);
		persona.setFechaActualizacion(LocalDateTime.now());
		persona.setFechaNacimiento(LocalDate.parse(clienteWrap.fechaNacimiento));
		persona.setGenero(UtilFunc.getEnumFromString(GeneroEnum.class, clienteWrap.genero));
		persona.setNombre(clienteWrap.nombres);
		persona.setTelefono(clienteWrap.telefono);
		
		ClienteEntity cliente = clienteRepo.findById(persona.getId()).get();
		
		if ( cliente == null ) {
			
			cliente = new ClienteEntity();
			cliente.setCodigoCliente(generarCodigoCliente());
			cliente.setFechaCreacion(LocalDateTime.now());
			cliente.setPersona(persona);
			
		}
		
		persona.setEstatus(clienteWrap.estatus);
		cliente.setClave(clienteWrap.clave);
		cliente.setFechaActualizacion(LocalDateTime.now());
		
		personaRepo.save(persona);
		clienteRepo.save(cliente);
		
		return cliente;
	}
	
	@Override
	@Transactional()
	public ClienteEntity patch(Long id, ClienteWrap clienteWrap) throws Exception {
		
		PersonaEntity persona = personaRepo.findOne(PersonaSpecs.findByIdentificacion(clienteWrap.identificacion)).orElse(null);
		
		if (persona == null)
			throw new Exception("Cliente no existe");
		
		if (clienteWrap.direccion != null)
			persona.setDireccion(clienteWrap.direccion);

		if (clienteWrap.fechaNacimiento != null)
			persona.setFechaNacimiento(LocalDate.parse(clienteWrap.fechaNacimiento));
		
		if (clienteWrap.genero != null)
			persona.setGenero(UtilFunc.getEnumFromString(GeneroEnum.class, clienteWrap.genero));
		
		if (clienteWrap.nombres != null)
			persona.setNombre(clienteWrap.nombres);
		
		if (clienteWrap.telefono != null)
			persona.setTelefono(clienteWrap.telefono);
		
		if (clienteWrap.estatus != null)
			persona.setEstatus(clienteWrap.estatus);
		
		persona.setFechaActualizacion(LocalDateTime.now());
		
		ClienteEntity cliente = clienteRepo.findById(persona.getId()).get();
		
		if (cliente == null)
			throw new Exception("Cliente no existe");
		
		if (clienteWrap.clave != null)
			cliente.setClave(clienteWrap.clave);
		
		if (clienteWrap.estatus != null)
			cliente.setEstatus(clienteWrap.estatus);
		
		cliente.setFechaActualizacion(LocalDateTime.now());
		
		personaRepo.save(persona);
		clienteRepo.save(cliente);
		
		return cliente;
	}

	@Override
	@Transactional()
	public void delete(Long id) throws Exception {
		
		ClienteEntity cliente = clienteRepo.findById(id).get();
		
		if (cliente == null)
			throw new Exception("No existe cliente"); 
		
		PersonaEntity persona = personaRepo.findById(cliente.getPersona().getId()).get();
		
		clienteRepo.delete(cliente);
		personaRepo.delete(persona);
		
	}
	
	private String generarCodigoCliente() throws Exception {
		
		ParametroEntity parametro = parametroRepo.findOne(ParametroSpecs.findByCodigo(CodigoParametroEnum.CODIGO_CLIENTE.name())).orElse(null);
		
		if ( parametro == null )
			throw new Exception("No se puede generar codigo cliente ya que no existe el parametro");
		
		Integer valor = parametro.getValor();
		
		String codigoCliente = parametro.getPrefijo() + valor + parametro.getSufijo();
		
		parametro.setValor(valor + 1);
		
		parametroRepo.save(parametro);
		
		return codigoCliente;
		
	}

}
