package com.locopizza.https.loco_pizza.controller;

import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.locopizza.https.loco_pizza.model.Notifica;
import com.locopizza.https.loco_pizza.model.Offerta;
import com.locopizza.https.loco_pizza.model.Pizza;
import com.locopizza.https.loco_pizza.repository.IngredienteRepository;
import com.locopizza.https.loco_pizza.repository.NotificaRepository;
import com.locopizza.https.loco_pizza.repository.OffertaRepository;
import com.locopizza.https.loco_pizza.repository.PizzaRepository;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequestMapping("/pizze")
public class PizzaController {

    @Autowired
    private PizzaRepository pizzaRepository;

    @Autowired
    private OffertaRepository offertaRepository;

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private NotificaRepository notificaRepository;

    @GetMapping
    public String index(
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "page", defaultValue = "0") int page,
            Model model) {

        int pageSize = 10;
        Page<Pizza> pagedPizze;

        if (keyword != null && !keyword.isEmpty()) {
            pagedPizze = pizzaRepository.findByTitoloContainingIgnoreCase(keyword, PageRequest.of(page, pageSize));
        } else {
            pagedPizze = pizzaRepository.findAll(PageRequest.of(page, pageSize));
        }

        // Notifiche
        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);

        // Pagine
        model.addAttribute("pizze", pagedPizze.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", pagedPizze.getTotalPages());
        model.addAttribute("keyword", keyword);

        return "/pizze/index";
    }

    @GetMapping("/{id}")
    public String show(@PathVariable("id") Integer id,
            @RequestParam(value = "keyword", required = false) String keyword,
            Model model) {

        Pizza pizza = pizzaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pizza non trovata con id: " + id));

        List<Offerta> offerte;

        if (keyword != null && !keyword.isEmpty()) {
            offerte = pizza.getOfferte().stream()
                    .filter(o -> o.getTitolo().toLowerCase().contains(keyword.toLowerCase()))
                    .toList();
        } else {
            offerte = pizza.getOfferte();
        }

        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);

        model.addAttribute("keyword", keyword);
        model.addAttribute("pizza", pizza);
        model.addAttribute("offerte", offerte);

        return "/pizze/mostra";
    }

    @GetMapping("/creazione")
    public String create(Model model) {

        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredienti", ingredienteRepository.findAll());

        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);
        return "/pizze/creazione";
    }

    @PostMapping("/creazione")
    public String store(@Valid @ModelAttribute("pizza") Pizza formPizza, BindingResult bindingResult, Model model,
            @RequestParam("azione") String azione) {

        if (azione.equals("cancel")) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
            long nonLette = notificaRepository.countByLettaFalse();

            model.addAttribute("notifiche", notifiche);
            model.addAttribute("nonLette", nonLette);
            return "/pizze/creazione";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            return "/pizze/creazione";
        }

        pizzaRepository.save(formPizza);

        Notifica notifica = new Notifica();
        notifica.setMessaggio(formPizza.getTitolo());
        notifica.setDataCreazione(LocalDateTime.now());
        notifica.setLetta(false);
        notificaRepository.save(notifica);

        return "redirect:/pizze";
    }

    @GetMapping("/modifica/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("pizza", pizzaRepository.findById(id).get());
        model.addAttribute("ingredienti", ingredienteRepository.findAll());

        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);
        return "/pizze/modifica";
    }

    @PostMapping("/modifica/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult, Model model, @RequestParam("azione") String azione) {

        if (azione.equals("cancel")) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            return "/pizze/modifica";
        }
        if (bindingResult.hasErrors()) {
            model.addAttribute("ingredienti", ingredienteRepository.findAll());
            return "/pizze/modifica";
        }

        pizzaRepository.save(formPizza);

        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);

        return "redirect:/pizze";
    }

    @PostMapping("/elimina/{id}")
    public String delete(@PathVariable("id") Integer id) {
        Pizza pizzaDaCancellare = pizzaRepository.findById(id).get();

        for (Offerta offerta : pizzaDaCancellare.getOfferte()) {
            offertaRepository.delete(offerta);
        }

        pizzaRepository.delete(pizzaDaCancellare);

        return "redirect:/pizze";
    }

    @PostMapping("/elimina-multiple")
    public String deleteMultiple(@RequestParam("selectedIds") List<Integer> ids) {
        for (Integer id : ids) {
            pizzaRepository.findById(id).ifPresent(pizza -> {
                // Cancella prima le offerte associate
                for (Offerta offerta : pizza.getOfferte()) {
                    offertaRepository.delete(offerta);
                }

                // Poi cancella la pizza
                pizzaRepository.delete(pizza);
            });
        }

        return "redirect:/pizze";
    }

}
