package com.locopizza.https.loco_pizza.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.locopizza.https.loco_pizza.model.Notifica;
import com.locopizza.https.loco_pizza.model.Ruolo;
import com.locopizza.https.loco_pizza.model.Utente;
import com.locopizza.https.loco_pizza.repository.NotificaRepository;
import com.locopizza.https.loco_pizza.repository.RuoloRepository;
import com.locopizza.https.loco_pizza.repository.UtenteRepository;

@Controller
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private NotificaRepository notificaRepository;

    @GetMapping("/creazione")
    public String create(Model model) {
        model.addAttribute("utente", new Utente());
        model.addAttribute("listaRuoli", ruoloRepository.findAll());
        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);
        return "utenti/creazione";
    }

    @PostMapping("/creazione")
    public String creaUtente(@ModelAttribute Utente utente,
            @RequestParam("ruoli") List<Integer> ruoliIds) {

        // carica i Ruolo dal DB
        Set<Ruolo> ruoliSelezionati = new HashSet<>(
                ruoloRepository.findAllById(ruoliIds));

        utente.setRuoli(ruoliSelezionati);

        utenteRepository.save(utente);
        return "redirect:/";
    }

}
