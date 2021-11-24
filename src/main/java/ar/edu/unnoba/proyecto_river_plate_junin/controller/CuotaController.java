package ar.edu.unnoba.proyecto_river_plate_junin.controller;


import org.springframework.stereotype.Controller;

import ar.edu.unnoba.proyecto_river_plate_junin.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class CuotaController {

    @Autowired
    private CuotaService CuotaService;

}