package ar.edu.unnoba.proyecto_river_plate_junin.utils;

import java.util.Calendar;
import java.util.Date;
import org.springframework.stereotype.Component;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
@Component
public class DateManager {
    
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
        c.add(Calendar.MONTH, 6);
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
}
