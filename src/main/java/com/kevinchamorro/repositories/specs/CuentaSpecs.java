package com.kevinchamorro.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.kevinchamorro.models.entities.CuentaEntity;

public class CuentaSpecs {

	public static Specification<CuentaEntity> findByNumeroCuenta(String numeroCuenta) {
		return (root, query, builder) -> {
			return builder.equal(root.get("numero_cuenta"), numeroCuenta);
		};
	}
	
	public static Specification<CuentaEntity> findByIdCliente(Long idCliente) {
		return (root, query, builder) -> {
			return builder.equal(root.get("id_cliente"), idCliente);
		};
	}
	
}
