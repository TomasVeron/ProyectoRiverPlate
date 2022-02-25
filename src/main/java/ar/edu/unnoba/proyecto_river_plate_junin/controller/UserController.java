package ar.edu.unnoba.proyecto_river_plate_junin.controller;


import ar.edu.unnoba.proyecto_river_plate_junin.service.*;
import ar.edu.unnoba.proyecto_river_plate_junin.model.User;

import java.util.List;

import javax.validation.Valid;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;



@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String loginUser(Model model, String err){
        model.addAttribute("user", new User());
        model.addAttribute("err", err);
        return "login";
    }

    @GetMapping("/register")
    public String registerUser(Model model, Authentication authentication){
        User sessionUser = (User)authentication.getPrincipal();
        if(sessionUser.getRol()==false){
            return "redirect:/users";
        }
        model.addAttribute("user", new User());
        return "register";
    }


    @PostMapping("/register")
    public String createUser(@Valid @ModelAttribute("user")User user, BindingResult result, ModelMap model){
        if (result.hasErrors()){
            model.addAttribute("user", user);
            return"/register";
        }
        try{
            userService.createUser(user);
        }catch(Exception e){
            model.addAttribute("formError", e.getMessage());
            model.addAttribute("user", user);
            return "/register";
        }

        return "redirect:/";
    }

    @GetMapping("/users")
    public String usersView(Model model,  Authentication authentication){
        User sessionUser = (User)authentication.getPrincipal();
        System.out.println(sessionUser.getRol());
        model.addAttribute("users", userService.getAllUsers());
        return "/users/users";

    }

    @GetMapping("/users/delete/{id}")
    public String usersDelete(@PathVariable("id") Long userId,  Authentication authentication, RedirectAttributes redirectAttributes){
        User user = userService.getUserById(userId);
        User sessionUser = (User)authentication.getPrincipal();
        if(sessionUser.getRol()==false){
            return "redirect:/users";
        }
        try {
            userService.deleteUser(user,sessionUser);
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("userDeleteError", e.getMessage());
        }
        return "redirect:/users";
    }


    @GetMapping("/users/buscarEnUsuario")
    public String buscarEnUsuarios (User user, Model model, String keyword) {
        if (keyword != null) {
            List<User> users = userService.buscarEnUsuario(keyword);
            model.addAttribute("users", users);
        }
        /* else {
            List<Usuario> listaUsuarios = usuarioService.listarUsuarios();
            model.addAttribute("listaUsuarios", listaUsuarios);
        } */
        return "/users/users";
    }


}
