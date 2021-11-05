package ar.edu.unnoba.proyecto_river_plate_junin.service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.*;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService{

    @Autowired
    private UserRepository repository;



    @Override
    public User findByNombre(String username) {
        return repository.findByNombre(username);
    }

    @Override
    public boolean authenticateUser(User user) throws Exception {
        boolean result = false;
        if (repository.findByNombre(user.getNombre())==null){
            throw new Exception("Usuario no encontrado");
        }
        else {
            result = true;
        }
        return result;

    }
}
