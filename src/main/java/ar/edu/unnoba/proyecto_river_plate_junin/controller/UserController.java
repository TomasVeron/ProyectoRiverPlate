package ar.edu.unnoba.proyecto_river_plate_junin.controller;

import ar.edu.unnoba.proyecto_river_plate_junin.service.*;
import ar.edu.unnoba.proyecto_river_plate_junin.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;



@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginUser(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @GetMapping("/loginUsernameInvalid")
    public String loginUserError(Model model){
        model.addAttribute("user", new User());
        return "loginUserNameInvalid";
    }

    @PostMapping("/login")
    public String validateUser(@Validated @ModelAttribute("user")User user, BindingResult result, Model model){
        if (result.hasErrors()){
            return "redirect:/";
        }
        else {
            try {
                if (userService.authenticateUser(user)){
                    return "/home";
                }
            }catch (Exception e){
                model.addAttribute("err", e.getMessage());
                return "redirect:/loginUsernameInvalid";
            }
        }
        return "redirect:/";
    }

}
