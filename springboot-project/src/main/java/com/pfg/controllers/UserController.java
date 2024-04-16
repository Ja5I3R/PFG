package com.pfg.controllers;

import javax.servlet.http.HttpSession;

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
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


@Controller
public class UserController {

    @Autowired
    private IUserService service;

    //Pagina de inicio
    @GetMapping({ "/" })
    public String redirect(Model model) {
        return "try_session";
    }

    //Listado de usuarios
    @GetMapping({ "/users" })
    public String listUsers(Model model) {
        model.addAttribute("users", service.listAllUsers());
        return "users";
    }

    //Creacion de nuevos usuarios
    @GetMapping("/users/new")
    public String showRegistration(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        return "create_user";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") User user) {
        service.createUser(user);
        return "redirect:/users";
    }

    //Borrado de usuarios
    @GetMapping("/users/{id}")
    public String deleteUser(@PathVariable Long id) {
        service.deleteUser(id);
        return "redirect:/users";
    }

    //Actualizacion de usuarios
    @GetMapping("/users/edit/{id}")
    public String showRegistration(@PathVariable Long id, Model model) {
        User user = service.readUserId(id);
        model.addAttribute("user", user);
        return "edit_user";
    }

    @PostMapping("/users/update")
    public String updateUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        
        User existingUser = service.readUserId(user.getId());
        existingUser.setName(user.getName());
        existingUser.setSurname(user.getSurname());
        existingUser.setEmail(user.getEmail());
        existingUser.setAge(user.getAge());
        existingUser.setGender(user.getGender());
        service.updateUser(user);
        return "redirect:/users";
    }

    //Comprobacion para inicio de sesion
    @PostMapping("/users/checkuser")
    public String checkUser(@ModelAttribute("user") User user, BindingResult bindingResult) {
        User userC = service.readUserName(user.getUsername());
        if (bindingResult.hasErrors()) {
            return "redirect:/try_session";
        }
        if (userC != null && user.getPassword().equals(userC.getPassword())) {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();

            HttpSession session = attr.getRequest().getSession(true);

            session.setAttribute("username", userC.getUsername());

            if(userC.getId_rol().equals(2L)){
                return "redirect:/users";
            }
            else{
                return "redirect:/test.html";
            }

        } else {
            return "redirect:/try_session"; 
        }
    }
    
}
