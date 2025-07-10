package com.locopizza.https.loco_pizza.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.locopizza.https.loco_pizza.model.EventoCalendario;
import com.locopizza.https.loco_pizza.model.Offerta;
import com.locopizza.https.loco_pizza.repository.OffertaRepository;

@Controller
public class CalendarioController {

    @Autowired
    private OffertaRepository offertaRepository;

    
    @GetMapping("/calendario")
    public String calendario() {
        return "/calendario/index"; 
    }

    // API JSON per eventi
    @GetMapping("/api/eventi-offerte")
    @ResponseBody
    public List<EventoCalendario> eventiOfferte() {
        List<Offerta> offerte = offertaRepository.findAll();

        return offerte.stream()
                .filter(offerta -> offerta.getDataInizio() != null && offerta.getDataFine() != null)
                .map(offerta -> new EventoCalendario(
                        offerta.getTitolo() + " - " + offerta.getPizza().getTitolo(),
                        offerta.getDataInizio().toString(),
                        offerta.getDataFine().toString()
                ))
                .toList();
    }
}
