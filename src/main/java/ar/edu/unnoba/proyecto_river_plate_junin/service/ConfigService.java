package ar.edu.unnoba.proyecto_river_plate_junin.service;

import org.springframework.stereotype.Service;

import ar.edu.unnoba.proyecto_river_plate_junin.configuration.SystemConfig;

@Service
public interface ConfigService {
    

    public SystemConfig getConfig();
    public SystemConfig editConfig(SystemConfig config) throws Exception;
}
