package com.kevinchamorro.services;

import java.util.List;

import com.kevinchamorro.models.entities.ParametroEntity;
import com.kevinchamorro.models.wrappers.ParametroWrap;

public interface IParametroService {
	List<ParametroEntity> get();
	ParametroEntity getById(Long id);
	ParametroEntity post(ParametroWrap parametroWrap) throws Exception;
	ParametroEntity put(Long id, ParametroWrap parametroWrap) throws Exception;
	ParametroEntity patch(Long id, ParametroWrap parametroWrap) throws Exception;
	void delete(Long id) throws Exception;
}
