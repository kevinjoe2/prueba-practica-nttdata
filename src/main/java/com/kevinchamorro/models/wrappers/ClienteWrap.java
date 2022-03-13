package com.kevinchamorro.models.wrappers;

import java.io.Serializable;

public class ClienteWrap implements Serializable {
	
	public String identificacion;
	public String nombres;
	public String genero;
	public String fechaNacimiento;
	public String telefono;
	public String direccion;
	public String clave;
	public Boolean estatus;

	private static final long serialVersionUID = 829031052373467977L;
	
}
