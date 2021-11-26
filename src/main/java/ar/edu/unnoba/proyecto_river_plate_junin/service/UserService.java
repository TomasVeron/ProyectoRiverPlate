package ar.edu.unnoba.proyecto_river_plate_junin.service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.*;


public interface UserService {

    public User findByNombre(String nombre);

    public User createUser(User usuario) throws Exception;

}
