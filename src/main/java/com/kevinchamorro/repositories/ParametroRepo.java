package com.kevinchamorro.repositories;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.kevinchamorro.models.entities.ParametroEntity;

public interface ParametroRepo extends CrudRepository<ParametroEntity, Long>, JpaSpecificationExecutor<ParametroEntity> {

}
