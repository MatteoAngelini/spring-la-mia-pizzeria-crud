package com.locopizza.https.loco_pizza.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.locopizza.https.loco_pizza.model.Pizza;
import com.locopizza.https.loco_pizza.repository.PizzaRepository;



@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizze = pizzaRepository.findAll();
        model.addAttribute("pizze", pizze);
        return "pizze/index";
    }
    



    
}
