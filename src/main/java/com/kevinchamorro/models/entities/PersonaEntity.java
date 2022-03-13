package com.kevinchamorro.models.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "personas")
public class PersonaEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "identificacion", length = 13)
	private String identificacion;
	
	@Column(name = "nombre", length = 128)
	String nombre;
	
	@Column(name = "genero", length = 1)
	private String genero;
	
	@Column(name = "fecha_nacimiento", columnDefinition = "DATE")
	private LocalDate fechaNacimiento; // En lugar de edad, optamos por poner la fecha de nacimiento y a partir de ello carcular la Edad.
	
	@Column(name = "direccion")
	private String direccion;
	
	@Column(name = "telefono",length = 16)
	private String telefono;
	
	@Column(name = "estatus")
	private Boolean estatus;
	
	@Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_actualizacion", columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaActualizacion;
		
	public int getEdad() {
		LocalDate fechaActual = LocalDate.now();
		Period periodo = Period.between(fechaNacimiento, fechaActual);
		return periodo.getYears();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public LocalDate getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(LocalDate fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getIdentificacion() {
		return identificacion;
	}

	public void setIdentificacion(String identificacion) {
		this.identificacion = identificacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getTelefono() {
		return telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Boolean getEstatus() {
		return estatus;
	}

	public void setEstatus(Boolean estatus) {
		this.estatus = estatus;
	}

	public LocalDateTime getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(LocalDateTime fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public LocalDateTime getFechaActualizacion() {
		return fechaActualizacion;
	}

	public void setFechaActualizacion(LocalDateTime fechaActualizacion) {
		this.fechaActualizacion = fechaActualizacion;
	}

	private static final long serialVersionUID = 5118153707946496077L;

}
