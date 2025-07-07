package com.locopizza.https.loco_pizza.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.locopizza.https.loco_pizza.model.Offerta;
import com.locopizza.https.loco_pizza.model.Pizza;
import com.locopizza.https.loco_pizza.repository.OffertaRepository;
import com.locopizza.https.loco_pizza.repository.PizzaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/offerte")
public class OffertaController {

    @Autowired
    private OffertaRepository offertaRepository;

    @Autowired
    private PizzaRepository pizzaRepository;


    @GetMapping("/creazione/{id}")
    public String creaOfferta(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata con ID " + id);
        }

        model.addAttribute("pizza", pizzaOptional.get());
        model.addAttribute("offerta", new Offerta());

        return "/offerte/creazione";
    }

    @PostMapping("/creazione/{id}")
    public String salvaOfferta(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("offerta") Offerta formOfferta,
            BindingResult bindingResult,
            @RequestParam("azione") String azione,
            Model model) {

        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata con ID " + id);
        }

        if (azione.equals("cancel")) {
            return "/offerte/creazione";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", pizzaOptional.get());
            return "/offerte/creazione";
        }

        // Collega la pizza all'offerta
        formOfferta.setPizza(pizzaOptional.get());
        formOfferta.setId(null); // Forza creazione nuova offerta (non aggiornamento!)

        offertaRepository.save(formOfferta);

        return "redirect:/pizze/{id}";
    }

}
