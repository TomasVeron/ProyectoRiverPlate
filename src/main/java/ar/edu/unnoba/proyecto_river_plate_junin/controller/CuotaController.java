package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.service.CuotaService;
import ar.edu.unnoba.proyecto_river_plate_junin.service.SocioService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class CuotaController {

    @Autowired
    private CuotaService cuotaService;

    @Autowired
    private SocioService socioService;

    //hay que generar las cuotas solo a los socios individuales y titulares

    @GetMapping("/cuotas")
    public String cuotasView(Model model){
        List<Socio> socioNoDependientes= socioService.getSocioNoDependientes();
        model.addAttribute("socioNoDependientes", socioNoDependientes);
        return "/cuotas/cuotas";
    }


    @PostMapping("/cuotas/generarCuotas")
    public String generarCuotas() {
        List<Socio> socioNoDependientes= socioService.getSocioNoDependientes();
        cuotaService.generarCuotas(socioNoDependientes);
        return"redirect:/cuotas";

    }

}