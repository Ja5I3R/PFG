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
public class UserController {

    @Autowired
    private IUserService service;

    //Listado de usuarios
    @GetMapping({ "/usuarios", "/" })
    public String listUsers(Model model) {
        model.addAttribute("usuarios", service.listAllUsers());
        return "usuarios";
    }

    //Creacion de nuevos usuarios
    @GetMapping("/usuarios/nuevo")
    public String showRegistration(Model model) {
        User user = new User();
        model.addAttribute("usuario", user);
        return "crear_usuario";
    }

    @PostMapping("/usuarios")
    public String createUser(@ModelAttribute("usuario") User user) {
        service.createUser(user);
        return "redirect:/usuarios";
    }

    //Borrado de usuarios
    @GetMapping("/usuarios/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "redirect:/usuarios";
    }

    //Actualizacion de usuarios
    @GetMapping("/usuarios/editar/{id}")
    public String showRegistration(@PathVariable Long id, Model model) {
        User user = service.readUserId(id);
        model.addAttribute("usuario", user);
        return "editar_usuario";
    }

    @PostMapping("/usuarios/actualizar")
    public String updateUser(@ModelAttribute("usuario") User user, BindingResult bindingResult, Model model) {
        
        User existingUser = service.readUserId(user.getId());
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setAge(user.getAge());
        existingUser.setGender(user.getGender());
        service.updateUser(user);
        return "redirect:/usuarios";
    }
    
}
