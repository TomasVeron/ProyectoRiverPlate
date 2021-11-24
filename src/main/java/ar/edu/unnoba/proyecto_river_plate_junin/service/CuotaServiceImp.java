package ar.edu.unnoba.proyecto_river_plate_junin.service;
import org.springframework.stereotype.Service;

import ar.edu.unnoba.proyecto_river_plate_junin.repository.CuotaRepository;

import org.springframework.beans.factory.annotation.Autowired;


@Service
public class CuotaServiceImp implements CuotaService{
    
    @Autowired
    private CuotaRepository repository;
    
}