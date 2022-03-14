package com.kevinchamorro.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.kevinchamorro.models.entities.ParametroEntity;
import com.kevinchamorro.util.enums.CodigoParametroEnum;

public class ParametroSpecs {
	
	public static Specification<ParametroEntity> findByCodigo(CodigoParametroEnum codigo) {
		return (root, query, builder) -> {
			return builder.equal(root.get("codigo"), codigo);
		};
	}
	
}
