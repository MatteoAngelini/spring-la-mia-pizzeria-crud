package com.locopizza.https.loco_pizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.locopizza.https.loco_pizza.model.Notifica;
import com.locopizza.https.loco_pizza.repository.NotificaRepository;

@Controller
@RequestMapping
public class NotificaController {

    @Autowired
    private NotificaRepository notificaRepository;

    @PostMapping("/notifiche/segna-tutte-lette")
    @ResponseBody
    public ResponseEntity<?> segnaTutteComeLette() {
        List<Notifica> nonLette = notificaRepository.findByLettaFalse();
        for (Notifica notifica : nonLette) {
            notifica.setLetta(true);
        }
        notificaRepository.saveAll(nonLette);
        return ResponseEntity.ok().build();
    }
}
