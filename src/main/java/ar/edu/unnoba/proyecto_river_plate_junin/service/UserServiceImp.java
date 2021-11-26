package ar.edu.unnoba.proyecto_river_plate_junin.service;
import ar.edu.unnoba.proyecto_river_plate_junin.model.User;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserServiceImp implements UserService, UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public User findByEmail(String username) {
        return repository.findByEmail(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByEmail(username);
        if(user == null){
            throw new UsernameNotFoundException("El usuario " + username + " no existe");
        }
        return  user;
    }

    private boolean checkUsernameAvailable(User user) throws Exception {
		User userFound = repository.findByEmail(user.getNombre());
		if (userFound != null) {
			throw new Exception("Username no disponible");
		}
		return true;
	}

	private boolean checkPasswordValid(User user) throws Exception {
		if ( !user.getPassword().equals(user.getConfirmarPassword())) {
			throw new Exception("Password y Confirm Password no son iguales");
		}
		return true;
	}

    @Override
    public User createUser(User usuario) throws Exception {
        if(checkUsernameAvailable(usuario) && checkPasswordValid(usuario)){
            usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));
            usuario = repository.save(usuario);
        }
        return usuario;
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
