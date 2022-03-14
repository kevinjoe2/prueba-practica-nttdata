package com.kevinchamorro.services;

import java.util.List;

import com.kevinchamorro.models.entities.ParametroEntity;
import com.kevinchamorro.models.wrappers.ParametroWrap;

public interface IParametroService {
	List<ParametroEntity> get();
	ParametroEntity getById(Long id);
	ParametroEntity post(ParametroWrap parametroWrap);
	ParametroEntity put(Long id, ParametroWrap parametroWrap);
	ParametroEntity patch(Long id, ParametroWrap parametroWrap);
	void delete(Long id);
}
