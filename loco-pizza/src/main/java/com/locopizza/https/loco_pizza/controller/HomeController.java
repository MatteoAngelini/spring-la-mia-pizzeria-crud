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

        return "/home/index"; 
    }
}
