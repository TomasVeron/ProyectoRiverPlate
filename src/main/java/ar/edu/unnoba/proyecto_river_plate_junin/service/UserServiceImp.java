package ar.edu.unnoba.proyecto_river_plate_junin.service;
import ar.edu.unnoba.proyecto_river_plate_junin.model.User;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImp implements UserService, UserDetailsService{

    @Autowired
    private UserRepository repository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public User findByNombre(String username) {
        return repository.findByNombre(username);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = repository.findByNombre(username);
        if(user == null){
            throw new UsernameNotFoundException("El usuario " + username + " no existe");
        }
        return  user;
    }

    @Override
    public User createUser(User usuario) {
        usuario.setClave(bCryptPasswordEncoder.encode(usuario.getClave()));
        return repository.save(usuario);
    }
}