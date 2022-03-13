package com.kevinchamorro.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.kevinchamorro.models.entities.ParametroEntity;

public class ParametroSpecs {
	
	public static Specification<ParametroEntity> findByCodigo(String codigo) {
		return (root, query, builder) -> {
			return builder.equal(root.get("codigo"), codigo);
		};
	}
	
}
