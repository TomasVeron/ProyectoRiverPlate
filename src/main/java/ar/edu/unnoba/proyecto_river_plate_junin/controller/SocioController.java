package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.service.SocioService;

@Controller
public class SocioController {

@Autowired
private SocioService socioService;


@GetMapping("/socios")
public String sociosView(Model model){
    model.addAttribute("socios",socioService.getAllSocios());
    return "/socios/socios";
    }

@GetMapping("/socios/addSocio")
public String addSocioView(Model model){
    model.addAttribute("socio", new Socio());
    return "/socios/addSocio";
}

@PostMapping("/socios/addSocio")
    public String createUser(@Valid @ModelAttribute("socio")Socio socio, BindingResult result, ModelMap model){
        if (result.hasErrors()){
            model.addAttribute("socio", socio);
            return"/socios/addSocio";
        }
        try{
            socioService.createSocio(socio);
        }catch(Exception e){
            // model.addAttribute("formError", e.getMessage());
            model.addAttribute("socio", socio);
            return "/socios/addSocio";
        }

        return "redirect:/socios";
    }

    @PostMapping("/socios/update")
    public String update(@ModelAttribute("socio") Socio socio){
        
        socioService.updateSocio(socio);
        return "redirect:/socios";
    }

    @GetMapping("/socios/edit/{id}")
    public String socioEdit(@PathVariable("id") Socio socio, Model model){
        socio = socioService.getSocio(socio);
        model.addAttribute("socio",socio);
        
        return "/socios/editSocio";
    }
 
    @GetMapping("/socios/ver/{id}")
    public String verSocio(@PathVariable("id") Socio socio, Model model){
        socio = socioService.getSocio(socio);
        model.addAttribute("socio",socio);
        return "/socios/verSocio";
    }

    @GetMapping("/socios/buscarEnSocios")
    public String buscarEnSocios (Socio socio, Model model, String keyword) {
        if (keyword != null) {
            List<Socio> socios = socioService.buscarEnSocio(keyword);
            model.addAttribute("socios", socios);
        }
        /* else {
            List<Usuario> listaUsuarios = usuarioService.listarUsuarios();
            model.addAttribute("listaUsuarios", listaUsuarios);
        } */
        return "/socios/socios";
    }

    

}
