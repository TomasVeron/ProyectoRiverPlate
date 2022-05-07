package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ar.edu.unnoba.proyecto_river_plate_junin.configuration.SystemConfig;
import ar.edu.unnoba.proyecto_river_plate_junin.service.ConfigService;

@Controller
public class ConfigController {
    @Autowired
    private ConfigService configService;

    //hay que generar las cuotas solo a los socios individuales y titulares

    @GetMapping("/configuracion")
    public String configView(Model model){
        SystemConfig config = configService.getConfig();
        model.addAttribute("config", config);
        return "configuracion/config";
    }

    @PostMapping("/configuracion/edit")
    public String cuotasEdit(@ModelAttribute("config")SystemConfig config, RedirectAttributes redirectAttributes){
        System.out.println(config);
        try {
            configService.editConfig(config);
            redirectAttributes.addFlashAttribute("success", "La configuracion se ha actualizado correctamente.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            System.out.println(e.getMessage());
        }
        return "redirect:/configuracion";
    }
}
