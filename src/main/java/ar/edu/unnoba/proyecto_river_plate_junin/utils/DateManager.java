package ar.edu.unnoba.proyecto_river_plate_junin.utils;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.service.ConfigService;
@Component
public class DateManager {

    @Autowired
    private ConfigService configService;
    
    public boolean validacionEdadMinima(Date fechaNacimiento, int edadMinima){
        Calendar fechaEdad = Calendar.getInstance();
        fechaEdad.setTime(fechaNacimiento);
        fechaEdad.add(Calendar.YEAR, edadMinima);
        Date fechaActual = new Date();

        if(fechaEdad.getTime().after(fechaActual)){
            return false; 
        }
        else{
            return true;
        }
    }

    public Date generarFechaCaducidad(Date fecha_creacion){
        Calendar c = Calendar.getInstance();
        c.setTime(fecha_creacion);
        c.add(Calendar.MONTH, configService.getConfig().getCantMesesVencimientoCuota());
        Date fechaCaducidad= c.getTime();
        return fechaCaducidad;
    }

    public Date getFechaLimite (Socio socio){
        Calendar fechaDisponible = Calendar.getInstance();
        fechaDisponible.setTime(socio.getFechaAlta());
        fechaDisponible.add(Calendar.MONTH, 1);
        Date fechaLimite = fechaDisponible.getTime();
        return fechaLimite;
    }

    public boolean desdeMayorQueHasta(Date desde, Date hasta){
        return desde.after(hasta);
    }

    public Date getLimiteFechaUltimaCuota(Cuota ultimaCuota){
        Calendar fechaUltimaDisponible = Calendar.getInstance();
        fechaUltimaDisponible.setTime(ultimaCuota.getFechaCreacion());
        fechaUltimaDisponible.add(Calendar.MONTH, 1);    
        Date limiteFechaUltimaCuota = fechaUltimaDisponible.getTime();
        return limiteFechaUltimaCuota;
    }

    public int getDiaDeFecha(Date fecha){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public int getMesDeFecha(Date fecha){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(fecha);
        return calendar.get(Calendar.MONTH);
    }

    public Date getfechaGeneracionCuotaDia(int dia){
        Calendar calendar=Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, dia);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }
}
