package com.kevinchamorro.services;

import java.util.List;

import com.kevinchamorro.models.entities.ClienteEntity;
import com.kevinchamorro.models.wrappers.ClienteWrap;

public interface IClienteService {
	
	List<ClienteWrap> getAll();
	ClienteWrap getById(Long id);
	ClienteEntity create(ClienteWrap nuevoCliente);
	ClienteEntity update(ClienteWrap cliente);
	void delete(Long id);

}
