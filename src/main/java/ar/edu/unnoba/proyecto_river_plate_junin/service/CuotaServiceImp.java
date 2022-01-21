package ar.edu.unnoba.proyecto_river_plate_junin.service;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Categoria;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.CuotaRepository;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
@Service
public class CuotaServiceImp implements CuotaService{
    
    @Autowired
    private CuotaRepository repository;


    public Date generarFechaCaducidad(){
        Date fecha = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(fecha);
        c.add(Calendar.MONTH, 6);
        Date fechaCaducidad= c.getTime();
        return fechaCaducidad;
    }

    @Override
    public Cuota generarCuotaSocio(Socio socio) throws Exception{//hay que comprobar que para generar una cuota haya pasado un mes de la ultima cuota
        if(socio.getSocioTitular()!=null){
            throw new Exception("No se puede generar cuotas para un socio dependiente");
        }
        System.out.println("holaalalalalalala");
        Cuota cuota = new Cuota();
        Long numeroCuota = repository.numeroCuota() + 1L ;
        Categoria categoria = socio.getCategoria();
        cuota.setNumero(String.valueOf(numeroCuota));
        cuota.setCategoria(categoria);
        cuota.setImporte(categoria.getValorCuota());
        cuota.setSocio(socio);
        cuota.setFechaCaducidad(generarFechaCaducidad());
        cuota.setFechaCreacion(new Date());
        repository.save(cuota);
        return cuota;
    }

    @Override
    public void generarCuotas(List<Socio> socioNoDependientes) {
        for(Socio socio: socioNoDependientes){
           try {
            generarCuotaSocio(socio);
        } catch (Exception e) {
            e.printStackTrace();
        }
        }    
    }
    
}