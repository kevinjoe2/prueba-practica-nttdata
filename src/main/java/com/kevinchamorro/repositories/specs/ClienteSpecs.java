package com.kevinchamorro.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.kevinchamorro.models.entities.ClienteEntity;

public class ClienteSpecs {
	
	public static Specification<ClienteEntity> findByIdPersona(Long idPersona) {
		return (root, query, builder) -> {
			return builder.equal(root.get("id_persona"), idPersona);
		};
	}

}
