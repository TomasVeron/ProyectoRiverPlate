package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.edu.unnoba.proyecto_river_plate_junin.service.SocioService;

@Controller
public class SocioController {

@Autowired
private SocioService socioService;
@GetMapping("/socios")
public String sociosView(Model model){
    model.addAttribute("socios",socioService.getAllSocios());
    return "/socios";



}}



