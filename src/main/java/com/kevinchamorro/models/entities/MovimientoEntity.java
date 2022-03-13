package com.kevinchamorro.models.entities;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "movimientos")
public class MovimientoEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@Column(name = "fecha_transaccion", columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaTrasaccion;
	
	@Column(name = "tipo_movimiento", length = 1)
	private String tipoMovimiento;
	
	@Column(name = "valor", columnDefinition = "NUMERIC(15,2)")
	private Double valor;
	
	@Column(name = "saldo", columnDefinition = "NUMERIC(15,2)")
	private Double saldo;
	
	@Column(name = "estatus")
	private Boolean estatus;
	
	@Column(name = "fecha_creacion", columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaCreacion;
	
	@Column(name = "fecha_actualizacion", columnDefinition = "TIMESTAMP")
	private LocalDateTime fechaActualizacion;
	
	@ManyToOne
	@JoinColumn(name = "id_cuenta")
	private CuentaEntity cuenta;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDateTime getFechaTrasaccion() {
		return fechaTrasaccion;
	}

	public void setFechaTrasaccion(LocalDateTime fechaTrasaccion) {
		this.fechaTrasaccion = fechaTrasaccion;
	}

	public String getTipoMovimiento() {
		return tipoMovimiento;
	}

	public void setTipoMovimiento(String tipoMovimiento) {
		this.tipoMovimiento = tipoMovimiento;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
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

	public CuentaEntity getCuenta() {
		return cuenta;
	}

	public void setCuenta(CuentaEntity cuenta) {
		this.cuenta = cuenta;
	}

	private static final long serialVersionUID = -5596962079398221405L;

}
