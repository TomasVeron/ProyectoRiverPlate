package ar.edu.unnoba.proyecto_river_plate_junin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import ar.edu.unnoba.proyecto_river_plate_junin.configuration.SystemConfig;

@Repository
public interface ConfigRepository extends JpaRepository<SystemConfig, Long> {

    @Query(value = 
    "SELECT * FROM configuracion_sistema c WHERE c.id_config=1", nativeQuery = true)
    public SystemConfig getConfig();
}
