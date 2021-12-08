package ar.edu.unnoba.proyecto_river_plate_junin.service;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.SocioRepository;

@Service
public class SocioServiceImp implements SocioService{

    @Autowired
    private SocioRepository repository;
    @Override
    public List<Socio> getAllSocios() {
        return repository.filterSocios();
    
    
    }


    
}
