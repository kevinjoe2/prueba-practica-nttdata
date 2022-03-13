package com.kevinchamorro.repositories.specs;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.kevinchamorro.models.entities.MovimientoEntity;

public class MovimientoSpecs {
	
	public static Specification<MovimientoEntity> findByidCuenta(Long id_cuenta) {
		return (root, query, builder) -> {
			return builder.equal(root.get("id_cuenta"), id_cuenta);
		};
	}
	
	public static Specification<MovimientoEntity> findByidCuentaAndRangoFechas(Long id_cuenta, LocalDate fechaInicio, LocalDate fechaFin) {
		return (root, query, builder) -> {
			
			List<Predicate> patientLevelPredicates = new ArrayList<Predicate>();
			
			if ( id_cuenta != null )
				patientLevelPredicates.add(builder.equal(root.get("id_cuenta"), id_cuenta));
			
			if ( fechaInicio != null )
				patientLevelPredicates.add(builder.greaterThan(root.get("fecha_creacion"), fechaInicio));
			
			if ( fechaFin != null )
				patientLevelPredicates.add(builder.lessThan(root.get("fecha_creacion"), fechaFin));
			
			return builder.and(patientLevelPredicates.toArray(new Predicate[] {}));
		};
	}
	
}
