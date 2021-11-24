package ar.edu.unnoba.proyecto_river_plate_junin.service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.*;
import org.springframework.stereotype.Service;

@Service
public interface SocioService {

    public Socio findByNombre(String nombre);

    public Socio createUser(Socio socio) throws Exception;


}
