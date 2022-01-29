package ar.edu.unnoba.proyecto_river_plate_junin.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Long>  {

    @Query(value = 
    "SELECT COUNT(*) FROM cuotas c", nativeQuery = true)
    public Long numeroCuota();

    @Query(value = 
    "SELECT * FROM cuotas c WHERE c.socio=?1 and c.fecha_creacion=(SELECT MAX(fecha_creacion) from cuotas where socio=?1)", nativeQuery = true)
    public Cuota consulatarUltimaCuota(Long socioId);


    @Query(value = 
    "SELECT * FROM cuotas c WHERE c.socio=?1", nativeQuery = true) 
    public List<Cuota> getCuotasSocio(Long socioId);
    
}
