package ar.edu.unnoba.proyecto_river_plate_junin.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;

import ar.edu.unnoba.proyecto_river_plate_junin.configuration.SystemConfig;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Categoria;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.CuotaRepository;
import ar.edu.unnoba.proyecto_river_plate_junin.utils.DateManager;
import ar.edu.unnoba.proyecto_river_plate_junin.utils.SendEmail;
import ar.edu.unnoba.proyecto_river_plate_junin.utils.SocioException;
import ar.edu.unnoba.proyecto_river_plate_junin.utils.SociosException;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CuotaServiceImp implements CuotaService{

    @Autowired
    private DateManager dateManager;
    
    @Autowired
    private CuotaRepository repository;

    @Autowired
    private SocioService socioService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private SendEmail email;

    public void controlarFechaCuota(Long socioId, Date fechaCreacion) throws Exception{
        Cuota ultimaCuota= repository.consulatarUltimaCuota(socioId);
        SystemConfig config = configService.getConfig();
        Socio socio = socioService.getSocio(socioId);
        Date fechaGeneracionCuota = dateManager.getfechaGeneracionCuotaDia(config.getDiaGeneracionCuota());
        if(ultimaCuota!=null && dateManager.desdeMayorQueHasta(ultimaCuota.getFechaCreacion(), fechaGeneracionCuota)){
            throw new SocioException(socio,"Solo se puede hacer una couta por mes");
        }
        if (dateManager.desdeMayorQueHasta(fechaCreacion, fechaGeneracionCuota)==false){// aunque coincidan los meses o el anio la funcion comprueba que la fecha de creacion sea desp de la fecha limite
                throw new SocioException(socio,"Aun no es la fecha de generacion de cuota");
        }
    }

    @Override
    public Cuota generarCuotaSocio(Socio socio) throws Exception{//hay que comprobar que para generar una cuota haya pasado un mes de la ultima cuota
        if(socio.getEstado()==false){
            throw new SocioException(socio,"No se puede generar cuotas para un socio inactivo");
        }
        if(socio.isDependiente()){
            throw new SocioException(socio,"No se puede generar cuotas para un socio dependiente");
        }
        Date fechaCreacion= new Date();
        controlarFechaCuota(socio.getId(), fechaCreacion);
        Cuota cuota = new Cuota();
        Long numeroCuota = repository.numeroCuota() + 1L ;
        Categoria categoria = socio.getCategoria();
        cuota.setNumero(String.valueOf(numeroCuota));
        cuota.setCategoria(categoria);
        cuota.setImporte(categoria.getValorCuota());
        cuota.setSocio(socio);
        cuota.setFechaCaducidad(dateManager.generarFechaCaducidad(fechaCreacion));
        cuota.setFechaCreacion(fechaCreacion);
        repository.save(cuota);
        email.enviarEmailCuotas(socio, cuota);
        return cuota;
    }

    @ExceptionHandler
    @Override
    public void generarCuotas(List<Socio> socioNoDependientes) throws Exception {
        SociosException errores = new SociosException();
        for(Socio socio: socioNoDependientes){
            try {
                generarCuotaSocio(socio);
            }catch(SocioException e){
                 errores.addError(e.getSocio(), e.getMessage());
            } catch (Exception e) {
                throw new Exception(e.getMessage());
            }

        }
        if(!errores.getErrores().isEmpty()){
            throw errores;
        }
    }



    @Override
    public List<Cuota> getCuotasSocio(Long socioId) {
        return repository.getCuotasSocio(socioId);
    }

    @Override
    @Transactional
    public Cuota registrarPago(Cuota cuota) throws Exception {
        System.out.println(cuota);
        Cuota cDB = repository.findById(cuota.getIdCuota()).orElse(null);
        if( cuota.getDetallePago().equals("")==true && cuota.getFormaPago().equals("")==true){
            throw new Exception("Los campos detalle de pago y forma de pago no deben estar vacios.");
        }
        Date fechaHoy = new Date();
        cDB.setFechaPago(fechaHoy);
        cDB.setDetallePago(cuota.getDetallePago());
        cDB.setFormaPago(cuota.getFormaPago());
        repository.save(cDB);
        email.enviarRecibo(cuota.getSocio(), cuota);
        return cDB;
    }   
}