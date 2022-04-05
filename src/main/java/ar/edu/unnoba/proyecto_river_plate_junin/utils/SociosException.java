package ar.edu.unnoba.proyecto_river_plate_junin.utils;

import java.util.ArrayList;
import java.util.List;


import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;

public class SociosException extends Exception{


    private List<String> errores;

    public SociosException() {
        setErrores(new ArrayList<>());
    }

    public SociosException(List<String> errores) {
        this.errores = errores;
    }

    public List<String> getErrores() {
        return errores;
    }

    public void setErrores(List<String> errores) {
        this.errores = errores;
    }

    public void addError(Socio socio, String message){
        this.getErrores().add(socio.getNombre()+" "+ socio.getApellido()+", ERROR: "+ message);
    }

    
}
