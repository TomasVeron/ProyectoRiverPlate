package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import ar.edu.unnoba.proyecto_river_plate_junin.model.Categoria;
import ar.edu.unnoba.proyecto_river_plate_junin.service.CategoriaService;

@Controller
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    @GetMapping("/categorias")
    public String categoriasView(Model model){
        model.addAttribute("categorias", categoriaService.getCategorias());
        return "/categorias/categorias";
    }

    @GetMapping("/categorias/edit/{id}")
    public String socioEdit(@PathVariable("id") Categoria categoria, Model model){
        categoria = categoriaService.getCategoria(categoria);
        model.addAttribute("categoria",categoria);
        return "/categorias/editCategoria";
    }

    @PostMapping("/categorias/update")
    public String update(@ModelAttribute("categoria") Categoria categoria){
        categoriaService.updateCategoria(categoria);
        return "redirect:/categorias";
    }


    
}
