package com.kevinchamorro.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.kevinchamorro.models.entities.PersonaEntity;

public interface PersonaRepo extends CrudRepository<PersonaEntity, Long>, JpaSpecificationExecutor<PersonaEntity> {
	
}
