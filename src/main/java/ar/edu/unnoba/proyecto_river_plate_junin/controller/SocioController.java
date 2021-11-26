package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import ar.edu.unnoba.proyecto_river_plate_junin.service.*;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class SocioController {

    @Autowired
    private SocioService socioService;

}