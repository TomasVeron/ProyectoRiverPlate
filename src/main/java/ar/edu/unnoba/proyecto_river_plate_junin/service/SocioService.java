package ar.edu.unnoba.proyecto_river_plate_junin.service;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;

import java.util.List;


@Service
public interface SocioService {
    public List<Socio> getAllSocios();
    

}
