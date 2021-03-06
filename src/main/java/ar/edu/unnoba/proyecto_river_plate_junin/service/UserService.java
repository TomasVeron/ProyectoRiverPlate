package ar.edu.unnoba.proyecto_river_plate_junin.service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    public User findByEmail(String email);

    public User createUser(User usuario) throws Exception;

    public void deleteUser(User usuario, User sessionUser) throws Exception;

    public List<User> getAllUsers();

    public User getUserById(Long id);

    public List<User> buscarEnUsuario(String keyword);

    public int contarUsers();


}
