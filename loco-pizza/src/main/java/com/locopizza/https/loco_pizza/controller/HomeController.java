package com.locopizza.https.loco_pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;



@Controller
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public String getHome(Model model) {
        
        int aggiunte = 15;   
        int modificate = 8; 
        int eliminate = 5;   

        model.addAttribute("aggiunte", aggiunte);
        model.addAttribute("modificate", modificate);
        model.addAttribute("eliminate", eliminate);

        return "/home/index"; 
    }
}
