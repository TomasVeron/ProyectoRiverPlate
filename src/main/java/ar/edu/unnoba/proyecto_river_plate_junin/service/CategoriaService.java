package ar.edu.unnoba.proyecto_river_plate_junin.service;

import java.util.List;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Categoria;
import org.springframework.stereotype.Service;
 
@Service
public interface CategoriaService {

    public List<Categoria> getCategorias();

    public Categoria getCategoria(Categoria categoria);

    public Categoria updateCategoria(Categoria categoria);
    
}
