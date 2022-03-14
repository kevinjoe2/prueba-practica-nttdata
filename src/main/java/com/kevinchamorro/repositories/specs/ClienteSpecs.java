package com.kevinchamorro.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.kevinchamorro.models.entities.ClienteEntity;

public class ClienteSpecs {
	
	public static Specification<ClienteEntity> findByCodigoCliente(String codigoCliente) {
		return (root, query, builder) -> {
			return builder.equal(root.get("codigoCliente"), codigoCliente);
		};
	}

}
