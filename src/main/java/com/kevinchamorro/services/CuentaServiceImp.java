package com.kevinchamorro.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.kevinchamorro.exceptions.KchException;
import com.kevinchamorro.models.entities.ClienteEntity;
import com.kevinchamorro.models.entities.CuentaEntity;
import com.kevinchamorro.models.entities.MovimientoEntity;
import com.kevinchamorro.models.entities.ParametroEntity;
import com.kevinchamorro.models.entities.PersonaEntity;
import com.kevinchamorro.models.wrappers.CuentaWrap;
import com.kevinchamorro.models.wrappers.ReporteEstadoCuentaWrap;
import com.kevinchamorro.repositories.ClienteRepo;
import com.kevinchamorro.repositories.CuentaRepo;
import com.kevinchamorro.repositories.MovimientoRepo;
import com.kevinchamorro.repositories.ParametroRepo;
import com.kevinchamorro.repositories.PersonaRepo;
import com.kevinchamorro.repositories.specs.ClienteSpecs;
import com.kevinchamorro.repositories.specs.CuentaSpecs;
import com.kevinchamorro.repositories.specs.MovimientoSpecs;
import com.kevinchamorro.repositories.specs.ParametroSpecs;
import com.kevinchamorro.util.enums.CodigoParametroEnum;
import com.kevinchamorro.util.enums.TipoCuentaEnum;
import com.kevinchamorro.util.enums.TipoMovimientoEnum;
import com.kevinchamorro.util.func.UtilFunc;

@Service
public class CuentaServiceImp implements ICuentaService {
	
	@Autowired
	private CuentaRepo cuentaRepo;
	
	@Autowired
	private MovimientoRepo movimientoRepo;
	
	@Autowired
	private ClienteRepo clienteRepo;
	
	@Autowired
	private PersonaRepo personaRepo;
	
	@Autowired
	private ParametroRepo parametroRepo;
	
	@Override
	public List<CuentaEntity> get() {
		try {
			return (List<CuentaEntity>) cuentaRepo.findAll();
		} catch (Exception e) {
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
		}
	}

	@Override
	public CuentaEntity getById(Long id) {
		try {
			return cuentaRepo.findById(id).orElse(null);
		} catch (Exception e) {
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getLocalizedMessage());
		}
	}

	@Override
	public CuentaEntity post(CuentaWrap cuentaWrap) {
		
		try {
			ClienteEntity cliente = clienteRepo.findOne(ClienteSpecs.findByCodigoCliente(cuentaWrap.codigoCliente)).orElse(null);

			if (cliente == null)
				throw new Exception("No existe un cliente con la codigo ingresado");

			CuentaEntity cuenta = new CuentaEntity();
			cuenta.setNumeroCuenta(generarNumeroCuenta());
			cuenta.setTipoCuenta(UtilFunc.getEnumFromString(TipoCuentaEnum.class, cuentaWrap.tipoCuenta));
			cuenta.setSaldo(cuentaWrap.saldoInicial);
			cuenta.setEstatus(cuentaWrap.estatus);
			cuenta.setFechaCreacion(LocalDateTime.now());
			cuenta.setFechaActualizacion(LocalDateTime.now());
			cuenta.setCliente(cliente);
			
			cuentaRepo.save(cuenta);
			
			if ( cuentaWrap.saldoInicial > 0 ) {
				
				MovimientoEntity movimiento = new MovimientoEntity();
				movimiento.setCuenta(cuenta);
				movimiento.setEstatus(true);
				movimiento.setFechaActualizacion(LocalDateTime.now());
				movimiento.setFechaCreacion(LocalDateTime.now());
				movimiento.setFechaTrasaccion(LocalDateTime.now());
				movimiento.setSaldoDisponible(cuentaWrap.saldoInicial);
				movimiento.setSaldoInicial((double) 0);
				movimiento.setTipoMovimiento(TipoMovimientoEnum.D);
				movimiento.setValor(cuentaWrap.saldoInicial);
				movimientoRepo.save(movimiento);
				
			}

			return cuenta;
		} catch (Exception e) {
			
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getMessage());
			
		}
	}

	@Override
	public CuentaEntity put(Long id, CuentaWrap cuentaWrap) {
		
		try {
			
			CuentaEntity cuenta = cuentaRepo.findById(id).orElse(null);

			if (cuenta == null) {
				
				ClienteEntity cliente = clienteRepo.findOne(ClienteSpecs.findByCodigoCliente(cuentaWrap.codigoCliente)).orElse(null);

				if (cliente == null)
					throw new Exception("No existe un cliente con el codigo ingresado");
				
				cuenta = new CuentaEntity();
				cuenta.setNumeroCuenta(generarNumeroCuenta());
				cuenta.setSaldo(cuentaWrap.saldoInicial);
				cuenta.setFechaCreacion(LocalDateTime.now());
				cuenta.setCliente(cliente);
				
				cuenta.setEstatus(cuentaWrap.estatus);
				cuenta.setTipoCuenta(UtilFunc.getEnumFromString(TipoCuentaEnum.class, cuentaWrap.tipoCuenta));
				cuenta.setFechaActualizacion(LocalDateTime.now());
				
				cuentaRepo.save(cuenta);
				
				if ( cuentaWrap.saldoInicial > 0 ) {
					
					MovimientoEntity movimiento = new MovimientoEntity();
					movimiento.setCuenta(cuenta);
					movimiento.setEstatus(true);
					movimiento.setFechaActualizacion(LocalDateTime.now());
					movimiento.setFechaCreacion(LocalDateTime.now());
					movimiento.setFechaTrasaccion(LocalDateTime.now());
					movimiento.setSaldoDisponible(cuentaWrap.saldoInicial);
					movimiento.setSaldoInicial((double) 0);
					movimiento.setTipoMovimiento(TipoMovimientoEnum.D);
					movimiento.setValor(cuentaWrap.saldoInicial);
					movimientoRepo.save(movimiento);
					
				}
				
			} else {
				
				cuenta.setEstatus(cuentaWrap.estatus);
				cuenta.setTipoCuenta(UtilFunc.getEnumFromString(TipoCuentaEnum.class, cuentaWrap.tipoCuenta));
				cuenta.setFechaActualizacion(LocalDateTime.now());

				cuentaRepo.save(cuenta);
				
			}

			return cuenta;
			
		} catch (Exception e) {
			
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getMessage());
			
		}
		
	}

	@Override
	public CuentaEntity patch(Long id, CuentaWrap cuentaWrap) {
		
		try {
			
			CuentaEntity cuenta = cuentaRepo.findById(id).orElse(null);

			if (cuenta == null)
				throw new Exception("No existe la cuenta");
			
			if (cuentaWrap.estatus != null)
				cuenta.setEstatus(cuentaWrap.estatus);

			cuenta.setFechaActualizacion(LocalDateTime.now());

			cuentaRepo.save(cuenta);

			return cuenta;
			
		} catch (Exception e) {
			
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getMessage());
			
		}
	}

	@Override
	public void delete(Long id) {
		
		try {
			
			CuentaEntity cuenta = cuentaRepo.findById(id).orElse(null);

			if (cuenta == null)
				throw new Exception("No existe la cuenta");

			cuentaRepo.delete(cuenta);
			
		} catch (Exception e) {
			
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getMessage());
			
		}
	}
	
	@Override
	public List<ReporteEstadoCuentaWrap> generarReporteEstadoCuenta(String codigoCliente, String fechaIncio, String fechaFin) {
		
		try {
			
			LocalDate fechaIncioCast = LocalDate.parse(fechaIncio);
			LocalDate fechaFinCast = LocalDate.parse(fechaFin);
			
			List<ReporteEstadoCuentaWrap> reporte = new ArrayList<ReporteEstadoCuentaWrap>();
			
			ClienteEntity cliente = clienteRepo.findOne(ClienteSpecs.findByCodigoCliente(codigoCliente)).orElse(null);
			
			if ( cliente == null ) 
				throw new Exception("Cliente no existe");
			
			PersonaEntity persona = personaRepo.findById(cliente.getPersona().getId()).orElse(null);
			
			if ( persona == null ) 
				throw new Exception("Cliente no existe");
			
			List<CuentaEntity> cuentas = cuentaRepo.findAll(CuentaSpecs.findByIdCliente(cliente.getId()));
			
			for (CuentaEntity cuenta : cuentas) {
				
				System.out.println(fechaIncioCast.atStartOfDay());
				System.out.println(fechaFinCast.atTime(23, 59));
				
				List<MovimientoEntity> movimientos = movimientoRepo
						.findAll(MovimientoSpecs.findByidCuentaAndRangoFechas(cuenta.getId(), fechaIncioCast.atStartOfDay(), fechaFinCast.atTime(23, 59)));
				
				for (MovimientoEntity movimiento : movimientos) {
					
					ReporteEstadoCuentaWrap itemReporte = new ReporteEstadoCuentaWrap();
					itemReporte.fecha = movimiento.getFechaCreacion().toLocalDate();
					itemReporte.cliente = persona.getNombre();
					itemReporte.numeroCuenta = cuenta.getNumeroCuenta();
					itemReporte.tipoCuenta = cuenta.getTipoCuenta().name();
					itemReporte.saldoInicial = movimiento.getSaldoInicial();
					itemReporte.estatus = movimiento.getEstatus();
					itemReporte.movimiento = movimiento.getValor();
					itemReporte.saldoDisponible = movimiento.getSaldoDisponible();
					
					reporte.add(itemReporte);
					
				}
				
			}
			
			return reporte;
		
		} catch (DateTimeParseException e) {
			
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,"El formato de las fechas es incorrecto, formato aceptado (yyyy/MM/dd)");
			
		} catch (Exception e) {
			
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getMessage());
			
		}
		
	}
	
	private String generarNumeroCuenta() {
		
		try {
			
			ParametroEntity parametro = parametroRepo
					.findOne(ParametroSpecs.findByCodigo(CodigoParametroEnum.NUMERO_CUENTA)).orElse(null);

			if (parametro == null)
				throw new Exception("No se puede generar el numero de cuenta ya que no existe el parametro");

			Integer valor = parametro.getValor();

			String numeroCuenta = parametro.getPrefijo() + valor + parametro.getSufijo();

			parametro.setValor(valor + 1);

			parametroRepo.save(parametro);

			return numeroCuenta;
			
		} catch (Exception e) {
			
			throw new KchException("Kch-400",HttpStatus.BAD_REQUEST,e.getMessage());
			
		}
		
	}

}
