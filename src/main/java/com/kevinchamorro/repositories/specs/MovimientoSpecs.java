package com.kevinchamorro.repositories.specs;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;

import com.kevinchamorro.models.entities.MovimientoEntity;

public class MovimientoSpecs {
	
	public static Specification<MovimientoEntity> findByidCuenta(Long idCuenta) {
		return (root, query, builder) -> {
			return builder.equal(root.get("cuenta"), idCuenta);
		};
	}
	
	public static Specification<MovimientoEntity> findByidCuentaAndRangoFechas(Long idCuenta, LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		return (root, query, builder) -> {
			
			List<Predicate> patientLevelPredicates = new ArrayList<Predicate>();
			
			if ( idCuenta != null )
				patientLevelPredicates.add(builder.equal(root.get("cuenta"), idCuenta));
			
			if ( fechaInicio != null )
				patientLevelPredicates.add(builder.greaterThan(root.get("fechaCreacion"), fechaInicio));
			
			if ( fechaFin != null )
				patientLevelPredicates.add(builder.lessThan(root.get("fechaCreacion"), fechaFin));
			
			return builder.and(patientLevelPredicates.toArray(new Predicate[] {}));
		};
	}
	
}
