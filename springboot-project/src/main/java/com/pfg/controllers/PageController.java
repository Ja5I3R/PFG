package com.pfg.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class PageController {
    
        //PAGINA DE INICIO
        @GetMapping({ "/" , ""})
        public String redirect(Model model) {
            return "index";
        }
}
