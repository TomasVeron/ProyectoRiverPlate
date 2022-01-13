package ar.edu.unnoba.proyecto_river_plate_junin.repository;

import ar.edu.unnoba.proyecto_river_plate_junin.model.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface SocioRepository extends JpaRepository<Socio, Long> {


    public Socio findByNombre(String nombre);
    @Query(value = 
        "SELECT * FROM socios s order by s.estado ASC, s.apellido ASC, s.nombre ASC", nativeQuery = true)
    
    List<Socio> filterSocios();

    public String findByCodigo(String codigoSocio);

    public String findByDni(String dni);

    public Socio findByIdSocio(Long id);

    @Query(value = 
        "SELECT * FROM socios s WHERE lower(s.nombre) LIKE lower(CONCAT('%', ?1, '%'))" + 
        "OR lower(s.codigo_socio) LIKE lower(CONCAT('%', ?1, '%'))" + 
        "OR lower(s.apellido) LIKE lower(CONCAT('%', ?1, '%'))", nativeQuery = true)
    public List<Socio> searchSocios(String keyword);

    @Query(value = 
    "SELECT COUNT(id_socio) FROM socios s", nativeQuery = true)
    public int contarSocios();

    @Query(value = 
    "SELECT COUNT(id_socio) FROM socios s WHERE s.estado = true", nativeQuery = true)
    public int contarSociosActivos();

    @Query(value = 
    "SELECT COUNT(id_socio)"+
    "FROM socios s INNER JOIN categorias c ON s.categoria = c.id_categoria " +
    "WHERE UPPER(c.nombre) LIKE '%INDIVIDUAL%' ", nativeQuery = true)
    public int contarSociosInd();

    @Query(value = 
    "SELECT COUNT(id_socio)"+
    "FROM socios s INNER JOIN categorias c ON s.categoria = c.id_categoria " +
    "WHERE UPPER(c.nombre) LIKE '%GRUPO FAMILIAR%' ", nativeQuery = true)
    public int contarSociosGf();

    @Query(value = 
    "SELECT *"+
    "FROM socios s " +
    "WHERE lower(s.codigo_socio) = lower(CONCAT('', ?1, '')) and s.socio_titular is null ", nativeQuery = true)
    public Socio encontrarSocioTitular(String codigoSocio);
   
    @Query(value = "SELECT id_socio FROM socios s WHERE lower(s.codigo_socio) = lower(?1)", nativeQuery=true)
    public Long obtenerIdSocio(String codigoSocio);
    
    @Modifying
    @Query(value = 
        "UPDATE socio SET estado = ?1 WHERE lower(id_socio) = lower(?2)", nativeQuery = true)
    public void actualizarCuentas(boolean habilitado, Long codigo);

}
