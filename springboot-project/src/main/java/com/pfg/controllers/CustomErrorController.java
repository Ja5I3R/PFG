package com.pfg.controllers;

import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.pfg.models.User;
@Controller
public class CustomErrorController implements ErrorController {

    //OBTENER USUARIO DE SESION
    public User getSessionUser() {
        ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpSession session = attr.getRequest().getSession(false);
        User sessionUser = (User) session.getAttribute("user");
        return sessionUser;
    }
    
    //PAGINA DE ERROR (400 Y 500)
    @GetMapping("/error")
    public String errorPage(HttpServletRequest request, Model model){
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        //AÑADIDO DE DATOS A MODELO
        model.addAttribute("error", status);
        model.addAttribute("date", new Date());
        model.addAttribute("usersession", getSessionUser());
        return "error_page";
    }

}

