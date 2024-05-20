package com.pfg.controllers;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

public class PageController {
    
        // Pagina de inicio
        @GetMapping({ "/" , ""})
        public String redirect(Model model) {
            return "index";
        }
}
