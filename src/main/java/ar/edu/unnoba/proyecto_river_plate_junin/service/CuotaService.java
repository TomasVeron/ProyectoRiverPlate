package ar.edu.unnoba.proyecto_river_plate_junin.service;

import java.util.List;


import org.springframework.stereotype.Service;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
@Service
public interface CuotaService {

   public void generarCuotas(List<Socio> socioNoDependientes) throws Exception;

   public Cuota generarCuotaSocio(Socio socio) throws Exception;

   public List<Cuota> getCuotasSocio(Long socioId);

public Cuota registrarPago(Cuota cuota) throws Exception;
    
}

