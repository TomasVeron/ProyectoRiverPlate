package ar.edu.unnoba.proyecto_river_plate_junin.service;
import java.util.Date;
import java.util.List;

import javax.management.loading.PrivateClassLoader;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public Socio createSocio(Socio socio) throws Exception {
        if(socio.getCodigo()==repository.findByCodigo(socio.getCodigo()) || socio.getDni()==repository.findByDni(socio.getDni())){
            throw new Exception("el codigo o dni de socio no esta disponible");
        }
        socio.setFechaAlta(new Date());
        socio.setEstado(true);
        return repository.save(socio);
    }

    @Override
    @Transactional(readOnly = true)
    public Socio encontrarSocio(Socio socio){
        return repository.findById(socio.getId()).orElse(null); 
    }

    @Override
    public Socio updateSocio(Socio socio) {       
        Socio uDB = repository.findById(socio.getId()).orElse(null);
        uDB.setEmail(socio.getEmail());
        uDB.setDomicilio(socio.getDomicilio());
        uDB.setTelefono(socio.getTelefono());
        uDB.setCodigo(socio.getCodigo());
        uDB.setEstado(socio.getEstado());
        return repository.save(uDB);
    }

    public Socio getSocio (Socio socio) {
        return repository.findById(socio.getId()).orElse(null); 
    
    }


    @Override
    @Transactional(readOnly = true)
    public List<Socio> buscarEnSocio(String keyword){
        return repository.searchSocios(keyword);
   }
}
