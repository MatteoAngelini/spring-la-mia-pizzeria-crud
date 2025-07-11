package com.locopizza.https.loco_pizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.locopizza.https.loco_pizza.model.Notifica;
import com.locopizza.https.loco_pizza.repository.IngredienteRepository;
import com.locopizza.https.loco_pizza.repository.NotificaRepository;
import com.locopizza.https.loco_pizza.repository.OffertaRepository;
import com.locopizza.https.loco_pizza.repository.PizzaRepository;
import com.locopizza.https.loco_pizza.repository.UtenteRepository;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private OffertaRepository offertaRepository;

    @Autowired
    private NotificaRepository notificaRepository;

    @GetMapping
    public String getHome(Model model) {

        // Conteggi
        long totalePizze = pizzaRepository.count();
        long totaleIngredienti = ingredienteRepository.count();
        long totaleUtenti = utenteRepository.count();
        long totaleOfferte = offertaRepository.count();

        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("totalePizze", totalePizze);
        model.addAttribute("totaleIngredienti", totaleIngredienti);
        model.addAttribute("totaleUtenti", totaleUtenti);
        model.addAttribute("totaleOfferte", totaleOfferte);
        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);

        return "/home/index";
    }
}
