package ar.edu.unnoba.proyecto_river_plate_junin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.proyecto_river_plate_junin.configuration.SystemConfig;
import ar.edu.unnoba.proyecto_river_plate_junin.repository.ConfigRepository;

@Service
public class ConfigServiceImp implements ConfigService {

    @Autowired
    private ConfigRepository repository;

    @Override
    public SystemConfig getConfig() {
        return repository.getConfig();
    }

    @Override
    public SystemConfig editConfig(SystemConfig config) throws Exception {
        if(config.getCantDeCoutasGeneradas()<=0 || config.getCantMesesVencimientoCuota()<=0 || config.getCantSociosPorGF()<=0 || config.getDiaGeneracionCuota()<=0){
            throw new Exception("una de las opciones es menor o igual a cero ");
        }
        if(config.getCantDeCoutasGeneradas()>12){
            throw new Exception("la cantidad de cuotas generadas en el mes no puede ser mayor a 12 ");
        }
        if(config.getDiaGeneracionCuota()>28){
            throw new Exception("el dia de generacion de cuotas no puede ser mayor a 28");
        }
        return repository.save(config);
    }

}
