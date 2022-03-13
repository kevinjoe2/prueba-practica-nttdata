package com.kevinchamorro.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevinchamorro.models.entities.ParametroEntity;
import com.kevinchamorro.models.wrappers.ParametroWrap;
import com.kevinchamorro.repositories.ParametroRepo;
import com.kevinchamorro.util.enums.CodigoParametroEnum;
import com.kevinchamorro.util.func.UtilFunc;

@Service
public class ParametroService implements IParametroService {
	
	@Autowired
	ParametroRepo parametroRepo;

	@Override
	public List<ParametroEntity> get() {
		return (List<ParametroEntity>) parametroRepo.findAll();
	}

	@Override
	public ParametroEntity getById(Long id) {
		return parametroRepo.findById(id).orElse(null);
	}

	@Override
	public ParametroEntity post(ParametroWrap parametroWrap) throws Exception {
		
		ParametroEntity parametro = new ParametroEntity();
		
		parametro.setCodigo(UtilFunc.getEnumFromString(CodigoParametroEnum.class, parametroWrap.codigo));
		parametro.setDescripcion(parametroWrap.descripcion);
		parametro.setEstatus(parametroWrap.estatus);
		parametro.setFechaActualizacion(LocalDateTime.now());
		parametro.setFechaCreacion(LocalDateTime.now());
		parametro.setPrefijo(parametroWrap.prefijo);
		parametro.setSufijo(parametroWrap.sufijo);
		parametro.setValor(parametroWrap.valor);
		
		parametroRepo.save(parametro);
		
		return parametro;
	}

	@Override
	public ParametroEntity put(Long id, ParametroWrap parametroWrap) throws Exception {
		
		ParametroEntity parametro = parametroRepo.findById(id).orElse(null);
		
		if ( parametro == null ) {
			
			parametro = new ParametroEntity();
			parametro.setCodigo(UtilFunc.getEnumFromString(CodigoParametroEnum.class, parametroWrap.codigo));
			parametro.setFechaCreacion(LocalDateTime.now());
			
		}
		
		parametro.setDescripcion(parametroWrap.descripcion);
		parametro.setEstatus(parametroWrap.estatus);
		parametro.setFechaActualizacion(LocalDateTime.now());
		parametro.setPrefijo(parametroWrap.prefijo);
		parametro.setSufijo(parametroWrap.sufijo);
		parametro.setValor(parametroWrap.valor);
			
		return parametro;
	}

	@Override
	public ParametroEntity patch(Long id, ParametroWrap parametroWrap) throws Exception {
		
		ParametroEntity parametro = parametroRepo.findById(id).orElse(null);
		
		if ( parametro == null ) 
			throw new Exception("Parametro no existe");
		
		if ( parametroWrap.descripcion != null )
			parametro.setDescripcion(parametroWrap.descripcion);
		
		if ( parametroWrap.estatus != null )
			parametro.setEstatus(parametroWrap.estatus);
		
		if ( parametroWrap.prefijo != null )
			parametro.setPrefijo(parametroWrap.prefijo);
		
		if ( parametroWrap.sufijo != null )
			parametro.setSufijo(parametroWrap.sufijo);
		
		if ( parametroWrap.valor != null )
			parametro.setValor(parametroWrap.valor);
			
		
		parametro.setFechaActualizacion(LocalDateTime.now());
		
		return parametro;
	}

	@Override
	public void delete(Long id) throws Exception {
		
		ParametroEntity parametro = parametroRepo.findById(id).orElse(null);
		
		if ( parametro == null ) 
			throw new Exception("Parametro no existe");
		
		parametroRepo.delete(parametro);
		
	}

}
