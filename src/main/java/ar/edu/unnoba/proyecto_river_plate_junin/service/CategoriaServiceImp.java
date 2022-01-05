package ar.edu.unnoba.proyecto_river_plate_junin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Categoria;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.CategoriaRepository;

@Service
public class CategoriaServiceImp implements CategoriaService {

    @Autowired
    private CategoriaRepository repository;

    @Override
    public List<Categoria> getCategorias() {
        return repository.findAll();
    }

    @Override
    public Categoria getCategoria (Categoria categoria) {
        return repository.findById(categoria.getId()).orElse(null); 
    }

    @Override
    public Categoria updateCategoria(Categoria categoria) {
        Categoria uDB = repository.findById(categoria.getId()).orElse(null);
        uDB.setValorCuota(categoria.getValorCuota());
        return repository.save(uDB);
    }
    
}
