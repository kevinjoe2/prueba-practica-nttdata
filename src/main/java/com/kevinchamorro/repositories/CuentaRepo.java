package com.kevinchamorro.repositories;

import org.springframework.data.repository.CrudRepository;

import com.kevinchamorro.models.entities.CuentaEntity;

public interface CuentaRepo extends CrudRepository<CuentaEntity, Long> {

}
