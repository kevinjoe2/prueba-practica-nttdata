package com.kevinchamorro.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kevinchamorro.exceptions.KchException;
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
		try {
			return (List<MovimientoEntity>) movimientoRepo.findAll();
		} catch (Exception e) {
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
		}
	}

	@Override
	public MovimientoEntity getById(Long id) {
		try {
			return movimientoRepo.findById(id).orElse(null);
		} catch (Exception e) {
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
		}
	}

	@Override
	public MovimientoEntity post(MovimientoWrap movimientoWrap) {
		
		try {
			
			if ( movimientoWrap.valor < 0 )
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "No se acepta valores negativos");
			
			TipoMovimientoEnum tipoMovimientoEnum = UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento);
			
			CuentaEntity cuenta = cuentaRepo.findOne(CuentaSpecs.findByNumeroCuenta(movimientoWrap.numeroCuenta))
					.orElse(null);

			if (cuenta == null)
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Cuenta no existe");

			if (cuenta.getSaldo() == 0 && tipoMovimientoEnum == TipoMovimientoEnum.R)
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Saldo no disponible");

			Double saldoFinal = saldoFinal(tipoMovimientoEnum, movimientoWrap.valor, cuenta.getSaldo());

			if (saldoFinal < 0)
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Saldo no disponible");

			if (tipoMovimientoEnum == TipoMovimientoEnum.R)
				validarCupoDiario(cuenta.getId(), movimientoWrap.valor);

			MovimientoEntity movimiento = new MovimientoEntity();
			movimiento.setCuenta(cuenta);
			movimiento.setEstatus(movimientoWrap.estatus);
			movimiento.setFechaCreacion(LocalDateTime.now());
			movimiento.setFechaActualizacion(LocalDateTime.now());
			movimiento.setFechaTrasaccion(LocalDateTime.now());
			movimiento.setSaldoInicial(cuenta.getSaldo());
			movimiento.setSaldoDisponible(saldoFinal);
			movimiento.setTipoMovimiento(tipoMovimientoEnum);
			movimiento.setValor((tipoMovimientoEnum == TipoMovimientoEnum.R ? movimientoWrap.valor * -1 : movimientoWrap.valor));
			cuenta.setSaldo(saldoFinal);

			movimientoRepo.save(movimiento);
			cuentaRepo.save(cuenta);

			return movimiento;
			
		} catch (Exception e) {
			
			throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, e.getMessage());
			
		}
	}

	@Override
	public MovimientoEntity put(Long id, MovimientoWrap movimientoWrap) {
		
		try {
			
			if ( movimientoWrap.valor < 0 )
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "No se acepta valores negativos");
			
			TipoMovimientoEnum tipoMovimientoEnum = UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento);
			
			CuentaEntity cuenta = cuentaRepo.findOne(CuentaSpecs.findByNumeroCuenta(movimientoWrap.numeroCuenta))
					.orElse(null);

			if (cuenta == null)
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Cuenta no existe");

			if (cuenta.getSaldo() == 0 && tipoMovimientoEnum == TipoMovimientoEnum.R)
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Saldo no disponible");

			if (tipoMovimientoEnum == TipoMovimientoEnum.R)
				validarCupoDiario(cuenta.getId(), movimientoWrap.valor);

			MovimientoEntity movimiento = movimientoRepo.findById(id).orElse(null);
			
			Double saldoFinal = (double) 0;
			
			if (movimiento == null) {
				
				movimiento = new MovimientoEntity();
				movimiento.setCuenta(cuenta);
				movimiento.setFechaCreacion(LocalDateTime.now());
				
				saldoFinal = saldoFinal(tipoMovimientoEnum, movimientoWrap.valor, cuenta.getSaldo());
				
			} else {
				
				saldoFinal = saldoFinal(tipoMovimientoEnum, movimientoWrap.valor, cuenta.getSaldo() - movimiento.getValor());
				
			}

			movimiento.setEstatus(movimientoWrap.estatus);
			movimiento.setFechaActualizacion(LocalDateTime.now());
			movimiento.setFechaTrasaccion(LocalDateTime.now());
			movimiento.setValor((tipoMovimientoEnum == TipoMovimientoEnum.R ? movimientoWrap.valor * -1 : movimientoWrap.valor));
			movimiento.setSaldoInicial(cuenta.getSaldo());
			movimiento.setTipoMovimiento(tipoMovimientoEnum);
			
			if (saldoFinal < 0)
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Saldo no disponible");
			
			movimiento.setSaldoDisponible(saldoFinal);
			cuenta.setSaldo(saldoFinal);

			movimientoRepo.save(movimiento);
			cuentaRepo.save(cuenta);

			return movimiento;
		} catch (Exception e) {
			
			throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, e.getMessage());
			
		}
		
	}

	@Override
	public MovimientoEntity patch(Long id, MovimientoWrap movimientoWrap) {
		
		try {
			
			if ( movimientoWrap.valor < 0 )
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "No se acepta valores negativos");
			
			TipoMovimientoEnum tipoMovimientoEnum = UtilFunc.getEnumFromString(TipoMovimientoEnum.class, movimientoWrap.tipoMovimiento);
			
			MovimientoEntity movimiento = movimientoRepo.findById(id).orElse(null);

			if (movimiento == null)
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Movimiento no existe");

			if (movimientoWrap.valor != null && movimientoWrap.tipoMovimiento != null
					&& movimientoWrap.numeroCuenta != null) {

				CuentaEntity cuenta = cuentaRepo.findOne(CuentaSpecs.findByNumeroCuenta(movimientoWrap.numeroCuenta))
						.orElse(null);

				if (cuenta == null)
					throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Cuenta no existe");

				if (cuenta.getSaldo() == 0 && tipoMovimientoEnum == TipoMovimientoEnum.R)
					throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Saldo no disponible");

				if (tipoMovimientoEnum == TipoMovimientoEnum.R)
					validarCupoDiario(cuenta.getId(), movimientoWrap.valor);

				movimiento.setTipoMovimiento(tipoMovimientoEnum);
				
				Double saldoFinal = saldoFinal(tipoMovimientoEnum, movimientoWrap.valor, cuenta.getSaldo() - movimiento.getValor());
				
				movimiento.setValor((tipoMovimientoEnum == TipoMovimientoEnum.R ? movimientoWrap.valor * -1 : movimientoWrap.valor));
				
				if (saldoFinal < 0)
					throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Saldo no disponible");
				
				movimiento.setSaldoDisponible(saldoFinal);
				cuenta.setSaldo(saldoFinal);

				cuentaRepo.save(cuenta);
			}

			if (movimientoWrap.estatus != null)
				movimiento.setEstatus(movimientoWrap.estatus);

			movimiento.setFechaActualizacion(LocalDateTime.now());
			movimiento.setFechaTrasaccion(LocalDateTime.now());

			movimientoRepo.save(movimiento);

			return movimiento;
		} catch (Exception e) {
			
			throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, e.getMessage());
			
		}
	}

	@Override
	public void delete(Long id) {
		
		try {
			MovimientoEntity movimiento = movimientoRepo.findById(id).orElse(null);

			if (movimiento == null)
				throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "El movimiento no existe");

			CuentaEntity cuenta = cuentaRepo.findById(movimiento.getCuenta().getId()).orElse(null);

			if (cuenta != null) {
				cuenta.setSaldo(cuenta.getSaldo() + movimiento.getValor());
				cuentaRepo.save(cuenta);
			}

			movimientoRepo.delete(movimiento);
		} catch (Exception e) {
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
		}
		
		
	}
	
	private Double saldoFinal(TipoMovimientoEnum tipoMovimientoEnum, Double valor, Double saldo) {
		
		try {
			if (tipoMovimientoEnum == TipoMovimientoEnum.D)
				return saldo + valor;

			if (tipoMovimientoEnum == TipoMovimientoEnum.R)
				return saldo - valor;

			throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "No existe el tipo de movimiento");
		} catch (Exception e) {
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
		}
	}
	
	private void validarCupoDiario(Long idCuenta, Double valorRetirar) {
		
		try {
			ParametroEntity parametro = parametroRepo
					.findOne(ParametroSpecs.findByCodigo(CodigoParametroEnum.CUPO_LIMITE)).orElse(null);

			if (parametro != null) {

				List<MovimientoEntity> movimientos = movimientoRepo.findAll(MovimientoSpecs.findByidCuenta(idCuenta));
				
				List<Double> movimientosHoy = movimientos.stream()
						.filter(f -> f.getFechaCreacion().toLocalDate().equals(LocalDate.now())
								&& f.getTipoMovimiento() == TipoMovimientoEnum.R)
						.map(m -> m.getValor()).collect(Collectors.toList());

				Double sumaValoresHoy = movimientosHoy.stream().reduce((double) 0, (a, b) -> a + b);
				
				sumaValoresHoy = (sumaValoresHoy * -1) + valorRetirar;

				if (sumaValoresHoy > parametro.getValor())
					throw new KchException("Kch-400", HttpStatus.BAD_REQUEST, "Cupo diario Excedido");
			} 
		} catch (Exception e) {
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
		}
	}
	
}
