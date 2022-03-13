package com.kevinchamorro.repositories;

import org.springframework.data.repository.CrudRepository;

import com.kevinchamorro.models.entities.MovimientoEntity;

public interface MovimientoRepo extends CrudRepository<MovimientoEntity, Long> {

}
