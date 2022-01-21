package ar.edu.unnoba.proyecto_river_plate_junin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long>  {

    @Query(value = 
    "SELECT COUNT(*) FROM cuotas c", nativeQuery = true)
    public Long numeroCuota();
    
}
