package ar.edu.unnoba.proyecto_river_plate_junin.repository;

import ar.edu.unnoba.proyecto_river_plate_junin.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {


    public User findByEmail(String email);

    @Query(value = 
        "SELECT * FROM usuarios u WHERE lower(u.nombre) LIKE lower(CONCAT('%', ?1, '%'))" + 
        "OR lower(u.email) LIKE lower(CONCAT('%', ?1, '%'))" + 
        "OR lower(u.apellido) LIKE lower(CONCAT('%', ?1, '%'))", nativeQuery = true)
    public List<User> searchUsuarios(String keyword);

}
