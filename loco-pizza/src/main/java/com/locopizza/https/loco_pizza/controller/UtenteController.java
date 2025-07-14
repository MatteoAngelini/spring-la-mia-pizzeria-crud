package com.locopizza.https.loco_pizza.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.locopizza.https.loco_pizza.model.Notifica;
import com.locopizza.https.loco_pizza.model.Ruolo;
import com.locopizza.https.loco_pizza.model.Utente;
import com.locopizza.https.loco_pizza.repository.NotificaRepository;
import com.locopizza.https.loco_pizza.repository.RuoloRepository;
import com.locopizza.https.loco_pizza.repository.UtenteRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/utenti")
public class UtenteController {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloRepository ruoloRepository;

    @Autowired
    private NotificaRepository notificaRepository;


    @GetMapping
    public String index(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Utente> utenti;
        if (keyword != null && !keyword.isEmpty()) {
            utenti = utenteRepository.findByNomeContainingIgnoreCase(keyword);
        } else {
            utenti = utenteRepository.findAll();
        }
        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);
        model.addAttribute("utenti", utenti);
        model.addAttribute("keyword", keyword);
        return "utenti/index";
    }


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

    @GetMapping("/modifica/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("utente", utenteRepository.findById(id).get());

        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);
        return "/utenti/modifica";
    }

    @PostMapping("/modifica/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("utente") Utente formUtente,
            BindingResult bindingResult, Model model, @RequestParam("azione") String azione) {

        if (azione.equals("cancel")) {
            model.addAttribute("utenti", utenteRepository.findAll());
            return "/utenti/modifica";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("utenti", utenteRepository.findAll());
            return "/utenti/modifica";
        }

        utenteRepository.save(formUtente);

        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);

        return "redirect:/pizze";
    }



     @PostMapping("/elimina-multiple")
     public String deleteMultiple(@RequestParam("selectedIds") List<Integer> ids) {
         for (Integer id : ids) {
             
                 utenteRepository.deleteById(id);
             };

         return "redirect:/pizze";
     }
}
