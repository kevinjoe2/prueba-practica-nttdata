package com.kevinchamorro.services;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		return (List<CuentaEntity>) cuentaRepo.findAll();
	}

	@Override
	public CuentaEntity getById(Long id) {
		return cuentaRepo.findById(id).orElse(null);
	}

	@Override
	public CuentaEntity post(CuentaWrap cuentaWrap) throws Exception {
		
		ClienteEntity cliente = clienteRepo.findOne(ClienteSpecs.findByCodigoCliente(cuentaWrap.codigoCliente)).orElse(null);
		
		if ( cliente == null )
			throw new Exception("No existe un cliente con la cedula ingresada"); 
		
		CuentaEntity cuenta = new CuentaEntity();
		cuenta.setNumeroCuenta(generarNumeroCuenta());
		cuenta.setTipoCuenta(UtilFunc.getEnumFromString(TipoCuentaEnum.class, cuentaWrap.tipoCuenta));
		cuenta.setSaldo((double) cuentaWrap.saldoInicial);
		cuenta.setEstatus(cuentaWrap.estatus);
		cuenta.setFechaCreacion(LocalDateTime.now());
		cuenta.setFechaActualizacion(LocalDateTime.now());
		cuenta.setCliente(cliente);
		
		cuentaRepo.save(cuenta);
		
		return cuenta;
	}

	@Override
	public CuentaEntity put(Long id, CuentaWrap cuentaWrap) throws Exception {
		
		ClienteEntity cliente = clienteRepo.findOne(ClienteSpecs.findByCodigoCliente(cuentaWrap.codigoCliente)).orElse(null);
		
		if ( cliente == null )
			throw new Exception("No existe un cliente con la cedula ingresada"); 
		
		CuentaEntity cuenta = cuentaRepo.findById(id).orElse(null);
		
		if (cuenta == null) {
			cuenta = new CuentaEntity();
			cuenta.setNumeroCuenta(generarNumeroCuenta());
			cuenta.setFechaCreacion(LocalDateTime.now());
			cuenta.setCliente(cliente);
		}
		
		cuenta.setEstatus(cuentaWrap.estatus);
		cuenta.setTipoCuenta(UtilFunc.getEnumFromString(TipoCuentaEnum.class, cuentaWrap.tipoCuenta));
		cuenta.setSaldo((double) cuentaWrap.saldoInicial);
		cuenta.setFechaActualizacion(LocalDateTime.now());
	
		cuentaRepo.save(cuenta);
		
		return cuenta;
		
	}

	@Override
	public CuentaEntity patch(Long id, CuentaWrap cuentaWrap) throws Exception {
		
		CuentaEntity cuenta = cuentaRepo.findById(id).orElse(null);
		
		if (cuenta == null)
			throw new Exception("No existe la cuenta"); 
		
		if (cuentaWrap.tipoCuenta != null)
			cuenta.setTipoCuenta(UtilFunc.getEnumFromString(TipoCuentaEnum.class, cuentaWrap.tipoCuenta));
		
		if (cuentaWrap.saldoInicial != null)
			cuenta.setSaldo((double) cuentaWrap.saldoInicial);
		
		cuenta.setFechaActualizacion(LocalDateTime.now());
		
		cuentaRepo.save(cuenta);
		
		return cuenta;
	}

	@Override
	public void delete(Long id) throws Exception {
		
		CuentaEntity cuenta = cuentaRepo.findById(id).orElse(null);
		
		if (cuenta == null)
			throw new Exception("No existe la cuenta"); 
		
		cuentaRepo.delete(cuenta);
	}
	
	@Override
	public List<ReporteEstadoCuentaWrap> generarReporteEstadoCuenta(String codigoCliente, String fechaIncio, String fechaFin) throws Exception {
		
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
				
				List<MovimientoEntity> movimientos = movimientoRepo
						.findAll(MovimientoSpecs.findByidCuentaAndRangoFechas(cuenta.getId(), fechaIncioCast, fechaFinCast));
				
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
			
			throw new Exception("El formato de las fechas es incorrecto, formato aceptado (yyyy/MM/dd)");
			
		} catch (Exception e) {
			
			throw new Exception(e.getMessage());
			
		}
		
	}
	
	private String generarNumeroCuenta() throws Exception {
		
		ParametroEntity parametro = parametroRepo.findOne(ParametroSpecs.findByCodigo(CodigoParametroEnum.NUMERO_CUENTA.name())).orElse(null);
		
		if ( parametro == null )
			throw new Exception("No se puede generar el numero de cuenta ya que no existe el parametro");
		
		Integer valor = parametro.getValor();
		
		String numeroCuenta = parametro.getPrefijo() + valor + parametro.getSufijo();
		
		parametro.setValor(valor + 1);
		
		parametroRepo.save(parametro);
		
		return numeroCuenta;
		
	}

}
