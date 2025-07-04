package com.locopizza.https.loco_pizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.locopizza.https.loco_pizza.model.Pizza;
import com.locopizza.https.loco_pizza.repository.PizzaRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @GetMapping
    public String index(Model model) {
        List<Pizza> pizze = pizzaRepository.findAll();
        model.addAttribute("pizze", pizze);
        return "/pizze/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        return "/pizze/mostra";
    }

    @GetMapping("/creazione")
    public String create(Model model) {

        model.addAttribute("pizza", new Pizza());
        return "/pizze/creazione";
    }

    @PostMapping("/creazione")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model,
            @RequestParam("azione") String azione) {

        if (azione.equals("cancel")) {

            return "/pizze/creazione"; // oppure dove vuoi reindirizzare
        }
        if (bindingResult.hasErrors()) {
            return "/pizze/creazione";
        }

        pizzaRepository.save(formPizza);

        return "redirect:/pizze";
    }

    @GetMapping("/modifica/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        return "/pizze/modifica";
    }

    @PostMapping("/modifica/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult, Model model, @RequestParam("azione") String azione) {

        if (azione.equals("cancel")) {

            return "/pizze/modifica"; // oppure dove vuoi reindirizzare
        }
        if (bindingResult.hasErrors()) {

            return "/pizze/modifica";
        }

        pizzaRepository.save(formPizza);

        return "redirect:/pizze";
    }

    @PostMapping("/elimina/{id}")
    public String delete(@PathVariable("id") Integer id) {
        pizzaRepository.deleteById(id);
        return "redirect:/pizze";
    }

}
