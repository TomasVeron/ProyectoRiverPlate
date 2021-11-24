package ar.edu.unnoba.proyecto_river_plate_junin.controller;


import ar.edu.unnoba.proyecto_river_plate_junin.service.*;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller
public class SocioController {

    @Autowired
    private SocioService socioService;

    @GetMapping("/login")
    public String loginSocio(Model model, String err){
        model.addAttribute("socio", new Socio());
        model.addAttribute("err", err);
        return "login";
    }

    @GetMapping("/register")
    public String registerSocio(Model model){
        model.addAttribute("socio", new Socio());
        return "register";
    }



}
