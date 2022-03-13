package com.kevinchamorro.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kevinchamorro.models.entities.CuentaEntity;
import com.kevinchamorro.models.entities.MovimientoEntity;
import com.kevinchamorro.models.entities.ParametroEntity;
import com.kevinchamorro.models.wrappers.MovimientoWrap;
import com.kevinchamorro.repositories.CuentaRepo;
import com.kevinchamorro.repositories.MovimientoRepo;
import com.kevinchamorro.repositories.ParametroRepo;
import com.kevinchamorro.repositories.specs.CuentaSpecs;
import com.kevinchamorro.repositories.specs.MovimientoSpecs;
import com.kevinchamorro.repositories.specs.ParametroSpecs;
import com.kevinchamorro.util.enums.CodigoParametroEnum;
import com.kevinchamorro.util.enums.TipoMovimientoEnum;
import com.kevinchamorro.util.func.UtilFunc;

@Service
public class MovimientoServiceImp implements IMovimientoService {
	
	@Autowired
	private CuentaRepo cuentaRepo;
	
	@Autowired
	private ParametroRepo parametroRepo;
	
	@Autowired
	private MovimientoRepo movimientoRepo;

	@Override
	public List<MovimientoEntity> get() {
		return (List<MovimientoEntity>) movimientoRepo.findAll();
	}

	@Override
	public MovimientoEntity getById(Long id) {
		return movimientoRepo.findById(id).orElse(null);
	}

	@Override
	public MovimientoEntity post(MovimientoWrap movimientoWrap) throws Exception {
		
		CuentaEntity cuenta = cuentaRepo.findOne(CuentaSpecs.findByNumeroCuenta(movimientoWrap.numeroCuenta)).orElse(null);
		
		if ( cuenta == null )
			throw new Exception("Cuenta no existe");
		
		if ( cuenta.getSaldo() == 0 )
			throw new Exception("Saldo no disponible");
		
		Double saldoFinal = saldoFinal(movimientoWrap.tipoMovimiento, movimientoWrap.valor, cuenta.getSaldo());
		
		if ( saldoFinal < 0 )
			throw new Exception("Saldo no disponible");
		
		if ( UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento) == TipoMovimientoEnum.R )
			validarCupoDiario(cuenta.getId(), movimientoWrap.valor);
		
		MovimientoEntity movimiento = new MovimientoEntity();
		movimiento.setCuenta(cuenta);
		movimiento.setEstatus(movimientoWrap.estatus);
		movimiento.setFechaActualizacion(LocalDateTime.now());
		movimiento.setFechaCreacion(LocalDateTime.now());
		movimiento.setSaldoInicial(cuenta.getSaldo());
		movimiento.setSaldoDisponible(saldoFinal);
		movimiento.setTipoMovimiento(UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento));
		movimiento.setValor(movimientoWrap.valor);
		cuenta.setSaldo(saldoFinal);
		
		movimientoRepo.save(movimiento);
		cuentaRepo.save(cuenta);
		
		return movimiento;
	}

	@Override
	public MovimientoEntity put(Long id, MovimientoWrap movimientoWrap) throws Exception {
		
		CuentaEntity cuenta = cuentaRepo.findOne(CuentaSpecs.findByNumeroCuenta(movimientoWrap.numeroCuenta)).orElse(null);
		
		if ( cuenta == null )
			throw new Exception("Cuenta no existe");
		
		if ( cuenta.getSaldo() == 0 )
			throw new Exception("Saldo no disponible");
		
		Double saldoFinal = saldoFinal(movimientoWrap.tipoMovimiento, movimientoWrap.valor, cuenta.getSaldo());
		
		if ( saldoFinal < 0 )
			throw new Exception("Saldo no disponible");
		
		if ( UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento) == TipoMovimientoEnum.R )
			validarCupoDiario(cuenta.getId(), movimientoWrap.valor);
		
		MovimientoEntity movimiento = movimientoRepo.findById(id).orElse(null);
		
		if (movimiento == null) {
			movimiento = new MovimientoEntity();
			movimiento.setCuenta(cuenta);
			movimiento.setFechaCreacion(LocalDateTime.now());
		}
		
		movimiento.setEstatus(movimientoWrap.estatus);
		movimiento.setFechaActualizacion(LocalDateTime.now());
		movimiento.setSaldoInicial(cuenta.getSaldo());
		movimiento.setSaldoDisponible(saldoFinal);
		movimiento.setTipoMovimiento(UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento));
		movimiento.setValor(movimientoWrap.valor);
		cuenta.setSaldo(saldoFinal);
		
		movimientoRepo.save(movimiento);
		cuentaRepo.save(cuenta);
		
		return movimiento;
		
	}

	@Override
	public MovimientoEntity patch(Long id, MovimientoWrap movimientoWrap) throws Exception {
		
		MovimientoEntity movimiento = movimientoRepo.findById(id).orElse(null);
		
		if ( movimiento == null )
			throw new Exception("Movimiento no existe");
		
		if ( movimientoWrap.valor != null && movimientoWrap.tipoMovimiento != null &&  movimientoWrap.numeroCuenta != null ) {
			
			CuentaEntity cuenta = cuentaRepo.findOne(CuentaSpecs.findByNumeroCuenta(movimientoWrap.numeroCuenta)).orElse(null);
			
			if ( cuenta == null )
				throw new Exception("Cuenta no existe");
			
			if ( cuenta.getSaldo() == 0 )
				throw new Exception("Saldo no disponible");
			
			Double saldoFinal = saldoFinal(movimientoWrap.tipoMovimiento, movimientoWrap.valor, cuenta.getSaldo());
			
			if ( saldoFinal < 0 )
				throw new Exception("Saldo no disponible");
			
			if ( UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento) == TipoMovimientoEnum.R )
				validarCupoDiario(cuenta.getId(), movimientoWrap.valor);
			
			movimiento.setSaldoInicial(cuenta.getSaldo());
			movimiento.setSaldoDisponible(saldoFinal);
			movimiento.setTipoMovimiento(UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento));
			movimiento.setValor(movimientoWrap.valor);
			cuenta.setSaldo(saldoFinal);
			
			cuentaRepo.save(cuenta);
		}
		
		if ( movimientoWrap.estatus != null )
			movimiento.setEstatus(movimientoWrap.estatus);
		
		movimiento.setFechaActualizacion(LocalDateTime.now());
		
		movimientoRepo.save(movimiento);
		
		return movimiento;
	}

	@Override
	public void delete(Long id) throws Exception {
		
		MovimientoEntity movimiento = movimientoRepo.findById(id).orElse(null);
		
		if ( movimiento == null )
			throw new Exception("El movimiento no existe");
		
		
	}
	
	private Double saldoFinal(String tipoMovimiento, Double valor, Double saldo) throws Exception {
		
		if (tipoMovimiento == TipoMovimientoEnum.D.name())
			return saldo + valor;
		
		if (tipoMovimiento == TipoMovimientoEnum.R.name())
			return saldo - valor;
				
		throw new Exception("El tipo de movimiento no existe");
	}
	
	private void validarCupoDiario(Long id_cuenta, Double valorRetirar) throws Exception {
		
		ParametroEntity parametro = parametroRepo.findOne(ParametroSpecs.findByCodigo(CodigoParametroEnum.CUPO_LIMITE.name())).orElse(null);
		
		if ( parametro != null  ) {
			
			List<MovimientoEntity> movimientos = movimientoRepo.findAll(MovimientoSpecs.findByidCuenta(id_cuenta));
			
			List<Double> movimientosHoy = movimientos
					.stream()
					.filter(f -> f.getFechaCreacion().toLocalDate() == LocalDate.now() && f.getTipoMovimiento() == TipoMovimientoEnum.R)
					.map(m -> m.getValor())
					.collect(Collectors.toList());
			
			Double sumaValoresHoy = movimientosHoy.stream().reduce((double) 0, (a, b) -> a + b);
			
			if ( (sumaValoresHoy + valorRetirar) > parametro.getValor() )
				throw new Exception("Cupo diario Excedido");
		}
	}
	
}
