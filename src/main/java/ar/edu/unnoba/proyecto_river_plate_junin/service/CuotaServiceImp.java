package ar.edu.unnoba.proyecto_river_plate_junin.service;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Categoria;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.CuotaRepository;
import ar.edu.unnoba.proyecto_river_plate_junin.utils.SendEmail;

import java.util.Calendar;
import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CuotaServiceImp implements CuotaService{
    
    @Autowired
    private CuotaRepository repository;

    @Autowired
    private SocioService socioService;

    @Autowired
    private SendEmail email;
	

    public Date generarFechaCaducidad(){
        Date fecha = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.MONTH, 6);
        Date fechaCaducidad= c.getTime();
        return fechaCaducidad;
    }

    public boolean controlarFechaCuota(Long socioId, Date fechaCreacion){
        Cuota ultimaCuota= repository.consulatarUltimaCuota(socioId);
        Socio socio = socioService.getSocio(socioId);

        Calendar fechaAlta = Calendar.getInstance();
        Calendar fechaDisponible = Calendar.getInstance();
        Calendar fechaCreacionAux = Calendar.getInstance();
        fechaAlta.setTime(socio.getFechaAlta());
        fechaDisponible.setTime(socio.getFechaAlta());
        fechaCreacionAux.setTime(fechaCreacion);
        fechaDisponible.add(Calendar.MONTH, 1);
        Date fechaLimite = fechaDisponible.getTime();
        int mesFechaAlta = fechaAlta.get(Calendar.MONTH);
        int anioFechaAlta = fechaAlta.get(Calendar.YEAR);
        int mesCreacion = fechaCreacionAux.get(Calendar.MONTH);
        int anioCreacion = fechaCreacionAux.get(Calendar.YEAR);

        if (ultimaCuota == null && fechaCreacion.after(fechaLimite) &&  (mesCreacion != mesFechaAlta ||anioFechaAlta != anioCreacion )){
            return true;
        }

        Calendar fechaUltimaCuota = Calendar.getInstance();
        Calendar fechaUltimaDisponible = Calendar.getInstance();
        fechaUltimaDisponible.setTime(ultimaCuota.getFechaCreacion());
        fechaUltimaDisponible.add(Calendar.MONTH, 1);
        
        Date limiteFechaUltimaCuota = fechaUltimaDisponible.getTime();
        fechaUltimaCuota.setTime(ultimaCuota.getFechaCreacion());
        int mesUltimaCuota = fechaUltimaCuota.get(Calendar.MONTH);
        int anioUltimaCuota = fechaUltimaCuota.get(Calendar.YEAR);

        if(fechaCreacion.after(limiteFechaUltimaCuota) && (mesCreacion != mesUltimaCuota || anioCreacion != anioUltimaCuota)){
            return true;
        }
        return false;
    }

    @Override
    public Cuota generarCuotaSocio(Socio socio) throws Exception{//hay que comprobar que para generar una cuota haya pasado un mes de la ultima cuota
        if(socio.getSocioTitular()!=null){
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
        cuota.setFechaCaducidad(generarFechaCaducidad());
        cuota.setFechaCreacion(fecha_creacion);
        repository.save(cuota);
        email.enviarEmailCuotas(socio);
        return cuota;
    }



    @Override
    public void generarCuotas(List<Socio> socioNoDependientes) {
        for(Socio socio: socioNoDependientes){
           try {
            generarCuotaSocio(socio);
        } catch (Exception e) {
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
        return repository.save(cDB);
    }
    
}