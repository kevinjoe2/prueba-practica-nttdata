package com.kevinchamorro.models.wrappers;

import java.io.Serializable;
import java.time.LocalDate;

public class ReporteEstadoCuentaWrap implements Serializable {
	
	public LocalDate fecha;
	public String cliente;
	public String numeroCuenta;
	public String tipoCuenta;
	public Double saldoInicial;
	public Boolean estatus;
	public Double movimiento;
	public Double saldoDisponible;
		
	private static final long serialVersionUID = 6024612840608482950L;
	
}
