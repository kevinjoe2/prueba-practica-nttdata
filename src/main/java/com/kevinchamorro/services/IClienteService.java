package com.kevinchamorro.services;

import java.util.List;

import com.kevinchamorro.models.entities.ClienteEntity;
import com.kevinchamorro.models.wrappers.ClienteWrap;

public interface IClienteService {
	
	List<ClienteEntity> get();
	ClienteEntity getById(Long id);
	ClienteEntity post(ClienteWrap clienteWrap);
	ClienteEntity put(Long id, ClienteWrap clienteWrap);
	ClienteEntity patch(Long id, ClienteWrap clienteWrap);
	void delete(Long id);

}
