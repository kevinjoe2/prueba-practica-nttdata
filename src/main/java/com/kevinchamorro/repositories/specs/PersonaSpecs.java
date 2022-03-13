package com.kevinchamorro.repositories.specs;

import org.springframework.data.jpa.domain.Specification;

import com.kevinchamorro.models.entities.PersonaEntity;

public class PersonaSpecs {
	
	public static Specification<PersonaEntity> findByIdentificacion(String identificacion) {
		return (root, query, builder) -> {
			return builder.equal(root.get("identificacion"), identificacion);
		};
	}

}
