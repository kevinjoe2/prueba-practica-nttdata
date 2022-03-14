package com.kevinchamorro.services;

import java.util.List;

import com.kevinchamorro.models.entities.MovimientoEntity;
import com.kevinchamorro.models.wrappers.MovimientoWrap;

public interface IMovimientoService {
	
	List<MovimientoEntity> get();
	MovimientoEntity getById(Long id);
	MovimientoEntity post(MovimientoWrap MovimientoWrap);
	MovimientoEntity put(Long id, MovimientoWrap MovimientoWrap);
	MovimientoEntity patch(Long id, MovimientoWrap MovimientoWrap);
	void delete(Long id);

}
