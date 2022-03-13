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
	
	public ClienteWrap(String identificacion, String nombres, String genero, String fechaNacimiento, String telefono,
			String direccion, String clave) {
		super();
		this.identificacion = identificacion;
		this.nombres = nombres;
		this.genero = genero;
		this.fechaNacimiento = fechaNacimiento;
		this.telefono = telefono;
		this.direccion = direccion;
		this.clave = clave;
	}
	
	public ClienteWrap() {
		super();
	}

	private static final long serialVersionUID = 829031052373467977L;
	
}
