package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Cuota;
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

    @GetMapping("/cuotas/cuotaPdf/{id}")
    public String cuotaPdf(@PathVariable("id") Cuota cuota,Model model){
        model.addAttribute("cuota", cuota);
        return "/cuotas/cuotaPdf";
    }


    @PostMapping("/cuotas/generarCuotas")
    public String generarCuotas() {
        List<Socio> socioNoDependientes= socioService.getSocioNoDependientes();
        cuotaService.generarCuotas(socioNoDependientes);
        return"redirect:/cuotas";

    }

    @PostMapping("/cuotas/generarCuotaSocio/{id}")
    public String generarCuotaSocio(@PathVariable("id") Socio socio, RedirectAttributes redirectAttributes) {
        try {
            cuotaService.generarCuotaSocio(socio);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return"redirect:/cuotas";

    }

    @GetMapping("/cuotas/ver/socio/{id}")
    public String verCuotasSocio(@PathVariable("id") Socio socio,Model model){
        model.addAttribute("cuotas", cuotaService.getCuotasSocio(socio.getId()));
        return "/cuotas/listaCuotasSocio";
    }

    @GetMapping("/cuotas/ver/{id}")
    public String verCuotasSocio(@PathVariable("id") Cuota cuota,Model model){
        model.addAttribute("cuota", cuota);
        return "/cuotas/verCuota";
    }

    @GetMapping("/cuotas/socio/registrarPago/{id}")
    public String registrarPagoCuota(@PathVariable("id") Cuota cuota,Model model){
        model.addAttribute("cuota", cuota);
        return "/cuotas/registrarPago";
    }


    @PostMapping("/cuotas/registrarPago")
    public String registrarPago(@ModelAttribute("cuota")Cuota cuota) {
        try {
            cuotaService.registrarPago(cuota);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return"redirect:/cuotas/ver/socio/"+cuota.getSocio().getId();

    }

}