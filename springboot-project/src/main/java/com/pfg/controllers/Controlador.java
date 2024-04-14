package com.pfg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pfg.interfaceService.IUserService;
import com.pfg.models.User;

@Controller
public class Controlador {

    @Autowired
    private IUserService servicio;

    @GetMapping({ "/usuarios", "/" })
    public String listUsers(Model modelo) {
        modelo.addAttribute("usuarios", servicio.listAllUsers());
        return "usuarios";
    }

    @GetMapping("/usuarios/nuevo")
    public String showRegistration(Model modelo) {
        User usuario = new User();
        modelo.addAttribute("usuario", usuario);
        return "crear_usuario";
    }

    @PostMapping("/usuarios")
    public String createUser(@ModelAttribute("usuario") User usuario) {
        servicio.createUser(usuario);
        return "redirect:/usuarios";
    }

    @GetMapping("/usuarios/{id}")
    public String deleteUser(@PathVariable Long id) {
        servicio.deleteUser(id);
        return "redirect:/usuarios";
    }
}
