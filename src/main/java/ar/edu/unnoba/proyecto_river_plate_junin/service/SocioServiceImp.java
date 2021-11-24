package ar.edu.unnoba.proyecto_river_plate_junin.service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.SocioRepository;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class SocioServiceImp implements SocioService{

 
    @Autowired
    private SocioRepository repository;

    @Override
    public Socio findByNombre(String nombre) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Socio createUser(Socio socio) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }
    
}
