package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import ar.edu.unnoba.proyecto_river_plate_junin.service.SocioService;
import ar.edu.unnoba.proyecto_river_plate_junin.service.UserService;

@Controller
public class IndexController {

    @Autowired
    private SocioService socioService;

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String index(Model model){
        Long sociosTotales = socioService.contarSocios();
        int usuariosTotales = userService.contarUsers();
        int sociosActivos = socioService.contarSociosActivos();
        model.addAttribute("usuarios",usuariosTotales );
        model.addAttribute("sociosTotales", sociosTotales);
        model.addAttribute("sociosActivos", sociosActivos);
        model.addAttribute("sociosGf", socioService.contarSociosGf());
        model.addAttribute("sociosInd", socioService.contarSociosInd());
        return "index";
    }
    
}
