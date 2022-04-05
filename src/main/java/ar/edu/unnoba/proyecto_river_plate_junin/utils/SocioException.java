package ar.edu.unnoba.proyecto_river_plate_junin.utils;



import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;

public class SocioException extends Exception {
    
    private Socio socio;

    public SocioException ( Socio socio, String message){
        super(message);
        setSocio(socio);
    }



    public Socio getSocio() {
        return socio;
    }

    public void setSocio(Socio socio) {
        this.socio = socio;
    }




    



    
}
