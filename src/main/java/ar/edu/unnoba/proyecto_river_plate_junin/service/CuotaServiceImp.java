package ar.edu.unnoba.proyecto_river_plate_junin.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Categoria;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.CuotaRepository;
import ar.edu.unnoba.proyecto_river_plate_junin.utils.DateManager;
import ar.edu.unnoba.proyecto_river_plate_junin.utils.SendEmail;

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
    private SendEmail email;

    public boolean controlarFechaCuota(Long socioId, Date fechaCreacion) throws Exception{
        Cuota ultimaCuota= repository.consulatarUltimaCuota(socioId);
        Socio socio = socioService.getSocio(socioId);
        Date fechaLimite = dateManager.getFechaLimite(socio);

        if (ultimaCuota==null){
            if (dateManager.desdeMayorQueHasta(fechaCreacion,fechaLimite)==false){// aunque coincidan los meses o el anio la funcion comprueba que la fecha de creacion sea desp de la fecha limite
                throw new Exception("No ha pasado un mes desde que se dio de alta el socio");
            }
            return true;
        }  
        Date limiteFechaUltimaCuota = dateManager.getLimiteFechaUltimaCuota(ultimaCuota);
        if(dateManager.desdeMayorQueHasta(fechaCreacion, limiteFechaUltimaCuota)){
            return true;
        }
        return false;
    }

    @Override
    public Cuota generarCuotaSocio(Socio socio) throws Exception{//hay que comprobar que para generar una cuota haya pasado un mes de la ultima cuota
        if(socio.isDependiente()){
            throw new Exception("No se puede generar cuotas para un socio dependiente");
        }
        Date fecha_creacion= new Date();
        if(!controlarFechaCuota(socio.getId(), fecha_creacion)){
            throw new Exception("No se pueden generar mas de una cuota por mes");
        }
        Cuota cuota = new Cuota();
        Long numeroCuota = repository.numeroCuota() + 1L ;
        Categoria categoria = socio.getCategoria();
        cuota.setNumero(String.valueOf(numeroCuota));
        cuota.setCategoria(categoria);
        cuota.setImporte(categoria.getValorCuota());
        cuota.setSocio(socio);
        cuota.setFechaCaducidad(dateManager.generarFechaCaducidad(fecha_creacion));
        cuota.setFechaCreacion(fecha_creacion);
        repository.save(cuota);
        email.enviarEmailCuotas(socio, cuota);
        return cuota;
    }

    @Override
    public void generarCuotas(List<Socio> socioNoDependientes) {
        for(Socio socio: socioNoDependientes){
           try {
            generarCuotaSocio(socio);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
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
        email.enviarRecibo(cuota.getSocio(), cuota);
        return repository.save(cDB);
    }   
}