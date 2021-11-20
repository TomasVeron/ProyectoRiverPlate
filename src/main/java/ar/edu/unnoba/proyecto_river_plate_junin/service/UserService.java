package ar.edu.unnoba.proyecto_river_plate_junin.service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.*;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User findByNombre(String nombre);

    public User createUser(User usuario) throws Exception;


}
