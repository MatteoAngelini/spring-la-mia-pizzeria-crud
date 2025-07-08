package com.locopizza.https.loco_pizza.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String login(Model model) {
        return "/auth/login"; 
    }
    @GetMapping("/logout")
    public String logout(Model model) {
        return "/auth/logout"; 
    }
}

