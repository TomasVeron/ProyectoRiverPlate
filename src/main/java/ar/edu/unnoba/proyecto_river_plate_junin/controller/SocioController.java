package ar.edu.unnoba.proyecto_river_plate_junin.controller;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Categoria;
import ar.edu.unnoba.proyecto_river_plate_junin.model.Socio;
import ar.edu.unnoba.proyecto_river_plate_junin.model.User;
import ar.edu.unnoba.proyecto_river_plate_junin.service.CategoriaService;
import ar.edu.unnoba.proyecto_river_plate_junin.service.SocioService;

@Controller
public class SocioController {

@Autowired
private SocioService socioService;

@Autowired
private CategoriaService categoriaService;



@GetMapping("/socios")
public String sociosView(Model model){
    model.addAttribute("socios",socioService.getAllSocios());
    return "socios/socios";
    }

@GetMapping("/socios/addSocio")
public String addSocioView(Model model1, Model model2){
    model1.addAttribute("socio", new Socio());
    List<Categoria> categorias = categoriaService.getCategorias();
    model2.addAttribute("categorias",categorias);
    return "socios/addSocio";
}

@PostMapping("/socios/addSocio")
    public String createUser(@Valid @ModelAttribute("socio")Socio socio, BindingResult result, ModelMap model,ModelMap model2){
        if (result.hasErrors()){
            model.addAttribute("socio", socio);
            model2.addAttribute("categorias",categoriaService.getCategorias());
            return"socios/addSocio";
        }
        try{
            socioService.createSocio(socio, socio.getCodigoSocioTitular());
        }catch(Exception e){
            model.addAttribute("socio", socio);
            model2.addAttribute("categorias", categoriaService.getCategorias());
            model2.addAttribute("error", e.getMessage());
            return "socios/addSocio";
        }

        return "redirect:/socios";
    }

    @PostMapping("/socios/update")
    public String update(@ModelAttribute("socio") Socio socio, Model model,RedirectAttributes redirectAttributes){
        try{
            socioService.updateSocio(socio);   
        }catch(Exception e){
            System.out.println(e.getMessage());
            model.addAttribute("socio", socio);
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/socios/edit/"+ socio.getId();
        }
        return "redirect:/socios";
    }

    @GetMapping("/socios/edit/{id}")
    public String socioEdit(@PathVariable("id") Socio socio, Model model, Authentication authentication){
        User sessionUser = (User)authentication.getPrincipal();
        if(sessionUser.getRol()==false){
            return "redirect:/socios";
        }
        socio = socioService.getSocio(socio);
        if(socio.getSocioTitular()!=null) socio.setCodigoSocioTitular(socio.getSocioTitular().getCodigo());
        model.addAttribute("socio",socio);
        model.addAttribute("categorias",categoriaService.getCategorias());
        
        return "socios/editSocio";
    }
 
    @GetMapping("/socios/ver/{id}")
    public String verSocio(@PathVariable("id") Socio socio, Model model){
        socio = socioService.getSocio(socio);
        model.addAttribute("socio",socio);
        model.addAttribute("familiares",socioService.getFamiliares(socio.getId()));
        return "socios/verSocio";
    }

    @GetMapping("/socios/buscarEnSocios")
    public String buscarEnSocios (Socio socio, Model model, String keyword) {
        if (keyword != null) {
            List<Socio> socios = socioService.buscarEnSocio(keyword);
            model.addAttribute("socios", socios);
        }
        return "socios/socios";
    }

}
