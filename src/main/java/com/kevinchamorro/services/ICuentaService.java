package com.kevinchamorro.services;

import java.util.List;

import com.kevinchamorro.models.entities.CuentaEntity;
import com.kevinchamorro.models.wrappers.CuentaWrap;
import com.kevinchamorro.models.wrappers.ReporteEstadoCuentaWrap;

public interface ICuentaService {
	
	List<CuentaEntity> get();
	CuentaEntity getById(Long id);
	CuentaEntity post(CuentaWrap cuentaWrap);
	CuentaEntity put(Long id, CuentaWrap cuentaWrap);
	CuentaEntity patch(Long id, CuentaWrap cuentaWrap);
	void delete(Long id);
	
	// REPORTES
	
	List<ReporteEstadoCuentaWrap> generarReporteEstadoCuenta(String codigoCliente, String fechaIncio, String fechaFin);

}
