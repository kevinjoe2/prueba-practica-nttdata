package com.kevinchamorro.services;

import com.kevinchamorro.models.wrappers.CuentaWrap;
import com.kevinchamorro.models.wrappers.ResponseWrap;

public interface ICuentaService {
	
	ResponseWrap crearCuentaCliente(CuentaWrap cuentaWrap) throws Exception;

}
