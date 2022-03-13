package com.kevinchamorro.services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevinchamorro.models.entities.ClienteEntity;
import com.kevinchamorro.models.entities.CuentaEntity;
import com.kevinchamorro.models.entities.PersonaEntity;
import com.kevinchamorro.models.wrappers.CuentaWrap;
import com.kevinchamorro.models.wrappers.ResponseWrap;
import com.kevinchamorro.repositories.ClienteRepo;
import com.kevinchamorro.repositories.CuentaRepo;
import com.kevinchamorro.repositories.PersonaRepo;
import com.kevinchamorro.repositories.specs.ClienteSpecs;
import com.kevinchamorro.repositories.specs.PersonaSpecs;

@Service
public class CuentaServiceImp implements ICuentaService {
	
	@Autowired
	private CuentaRepo cuentaRepo;
	
	@Autowired
	private PersonaRepo personaRepo;
	
	@Autowired
	private ClienteRepo clienteRepo;

	@Override
	public ResponseWrap crearCuentaCliente(CuentaWrap cuentaWrap) throws Exception {
		
		PersonaEntity persona = personaRepo.findOne(PersonaSpecs.findByIdentificacion(cuentaWrap.identifiacionCliente)).orElse(null);
		
		if ( persona == null )
			throw new Exception("No existe un cliente con la cedula ingresada"); 
		
		ClienteEntity cliente = clienteRepo.findOne(ClienteSpecs.findByIdPersona(persona.getId())).orElse(null);
		
		if ( cliente == null )
			throw new Exception("No existe un cliente con la cedula ingresada"); 
		
		CuentaEntity cuenta = new CuentaEntity();
		cuenta.setNumeroCuenta("450211");
		cuenta.setTipoCuenta(cuentaWrap.tipoCuenta);
		cuenta.setSaldo((double) 0);
		cuenta.setEstatus(true);
		cuenta.setFechaCreacion(LocalDateTime.now());
		cuenta.setFechaActualizacion(LocalDateTime.now());
		cuenta.setCliente(cliente);
		
		cuentaRepo.save(cuenta);
		
		return new ResponseWrap();
	}

}
