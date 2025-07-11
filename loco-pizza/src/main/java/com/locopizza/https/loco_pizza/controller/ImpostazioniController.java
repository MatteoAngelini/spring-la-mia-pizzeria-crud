package com.locopizza.https.loco_pizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.locopizza.https.loco_pizza.model.Notifica;
import com.locopizza.https.loco_pizza.repository.NotificaRepository;

@Controller
@RequestMapping("/impostazioni")
public class ImpostazioniController {

    @Autowired
    private NotificaRepository notificaRepository;

    @GetMapping
    public String getHome(Model model) {

        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);

        return "/impostazioni/index";
    }
}
