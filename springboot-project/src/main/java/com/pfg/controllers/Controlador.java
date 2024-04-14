package com.pfg.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pfg.interfaceService.IUserService;
import com.pfg.models.User;
import com.pfg.service.UserService;

import org.springframework.web.bind.annotation.RequestParam;


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

    //POR PROBAR
    @GetMapping("/usuarios/editar/{id}")
    public String showRegistration(@PathVariable Long id, Model modelo) {
        User usuario = servicio.readUserId(id);
        modelo.addAttribute("usuario", usuario);
        return "editar_usuario";
    }

    @PostMapping("/usuarios/actualizar")
    public String updateUser(@ModelAttribute("usuario") User usuario, BindingResult bindingResult, Model modelo) {
        
        User usuarioExistente = servicio.readUserId(usuario.getId());
        usuarioExistente.setName(usuario.getName());
        usuarioExistente.setSurname(usuario.getSurname());
        usuarioExistente.setEmail(usuario.getEmail());
        usuarioExistente.setAge(usuario.getAge());
        usuarioExistente.setGender(usuario.getGender());
        servicio.updateUser(usuario);
        return "redirect:/usuarios";
    }
    
}
