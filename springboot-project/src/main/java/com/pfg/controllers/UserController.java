package com.pfg.controllers;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.pfg.interfaceService.IEventDataService;
import com.pfg.interfaceService.IEventService;
import com.pfg.interfaceService.IInterestService;
import com.pfg.interfaceService.IUserDataService;
import com.pfg.interfaceService.IUserService;
import com.pfg.models.User;
import com.pfg.models.UserData;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;



@Controller
public class UserController {

    @Autowired
    private IUserService service;

    @Autowired
    private IInterestService intService;

    @Autowired
    private IUserDataService userDataService;

    @Autowired
    private IEventService eventService;

    @Autowired
    private IEventDataService eventDataService;

    //Pagina de inicio
    @GetMapping({ "/" })
    public String redirect(Model model) {
        return "index";
    }

    //Pagina de inicio de sesion
    @GetMapping({ "/users/login" })
    public String redirectLogIn(Model model) {
        return "try_session";
    }

    //Cierre de sesion
    @GetMapping({ "/users/logout" })
    public String logout(Model model) {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        session.invalidate();
        return "redirect:/";
    }

    //Pagina de usuario
    @GetMapping({ "/userpage/{id}" })
    public String userPage(Model model, @PathVariable Long id) {
        User userC = service.readUserId(id);
        model.addAttribute("user", userC);
        model.addAttribute("interestList", intService.listByIndexes(userDataService.getInterestList(userC.getId())));
        model.addAttribute("eventList", eventService.listByIndexes(eventDataService.getEventData(userC.getId())));
        return "user_page";
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
        model.addAttribute("interestList", intService.listAllInterest());
        return "create_user";
    }

    @PostMapping("/users")
    public String createUser(@ModelAttribute("user") User user, BindingResult bindingResult, HttpServletRequest request) {
        User userCreated = service.readUserName(user.getUsername());
        if(userCreated == null){
            userCreated = service.readEmail(user.getEmail());
        }
        if(userCreated != null){
            return "redirect:/users/new";
        }
        else{
            service.createUser(user);
            String[] interestList = request.getParameterValues("interests");
            if(interestList != null){            
                UserData UD = new UserData();
                UD.setUser_id(user.getId());
                UD.setInterest1_id(Long.valueOf(interestList[0]));
                UD.setInterest2_id(Long.valueOf(interestList[1]));
                UD.setInterest3_id(Long.valueOf(interestList[2]));
                UD.setInterest4_id(Long.valueOf(interestList[3]));
                UD.setInterest5_id(Long.valueOf(interestList[4]));
                UD.setReport_number(0L);
                userDataService.saveUserPreferences(UD);

                ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
                HttpSession session = attr.getRequest().getSession(false);

                if(session == null){ //CASO SI ES UN USUARIO NUEVO DESDE EL INDEX
                    session = attr.getRequest().getSession(true);
                    session.setAttribute("user", user);
                    return "user_page";
                }
                else{
                    User sessionUser = (User)session.getAttribute("user");
                    if(sessionUser.getId_rol().equals(2L)){
                        return "redirect:/users";
                    }
                    else{
                        return "user_page";
                    }
                }
                
            }
            return "redirect:/users";
        }
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
        existingUser.setBirthdate(user.getBirthdate());
        service.updateUser(existingUser);

        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User)session.getAttribute("user");
        if(sessionUser.getId_rol().equals(2L)){
            return "redirect:/users";
        }
        else{
            return "user_page";
        }
        
    }

    //Comprobacion para inicio de sesion
    @PostMapping("/users/checkuser")
    public String checkUser(@ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        User userC = service.readUserName(user.getUsername());
        if (bindingResult.hasErrors()) {
            return "redirect:/try_session";
        }
        if (userC != null && user.getPassword().equals(userC.getPassword())) {
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession session = attr.getRequest().getSession(true);
            session.setAttribute("user", userC);

            if(userC.getId_rol().equals(2L)){
                return "redirect:/users";
            }
            else{
                model.addAttribute("user", userC);
                model.addAttribute("interestList", intService.listByIndexes(userDataService.getInterestList(userC.getId())));
                model.addAttribute("eventList", eventService.listByIndexes(eventDataService.getEventData(userC.getId())));
                return "user_page";
            }

        } else {
            return "try_session"; 
        }
    }
    
}
