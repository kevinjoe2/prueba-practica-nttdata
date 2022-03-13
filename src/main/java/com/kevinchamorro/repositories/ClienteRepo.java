package com.kevinchamorro.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.kevinchamorro.models.entities.ClienteEntity;

public interface ClienteRepo extends CrudRepository<ClienteEntity, Long>, JpaSpecificationExecutor<ClienteEntity> {
	
}
