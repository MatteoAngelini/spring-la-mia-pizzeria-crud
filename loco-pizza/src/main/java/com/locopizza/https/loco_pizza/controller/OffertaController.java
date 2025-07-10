package com.locopizza.https.loco_pizza.controller;

import java.time.format.DateTimeFormatter;
import java.util.List;
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

    @GetMapping
    public String index(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Offerta> offerte;
        if (keyword != null && !keyword.isEmpty()) {
            offerte = offertaRepository.findByTitoloContainingIgnoreCase(keyword);
        } else {
            offerte = offertaRepository.findAll();
        }
        model.addAttribute("offerte", offerte);
        model.addAttribute("keyword", keyword);
        return "offerte/index";
    }

    @GetMapping("/creazione/{id}")
    public String create(@PathVariable("id") Integer id, Model model) {
        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata con ID " + id);
        }

        model.addAttribute("pizza", pizzaOptional.get());
        model.addAttribute("offerta", new Offerta());

        return "/offerte/creazione";
    }

    @PostMapping("/creazione/{id}")
    public String store(@PathVariable("id") Integer id,
            @Valid @ModelAttribute("offerta") Offerta formOfferta,
            BindingResult bindingResult,
            @RequestParam("azione") String azione,
            Model model) {

        Optional<Pizza> pizzaOptional = pizzaRepository.findById(id);
        if (pizzaOptional.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pizza non trovata con ID " + id);
        }

        if (azione.equals("cancel")) {
            return "redirect:/pizze/" + id;
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pizza", pizzaOptional.get());
            return "/offerte/creazione";
        }

        formOfferta.setPizza(pizzaOptional.get());
        formOfferta.setId(null); // Per assicurarti che venga salvata come nuova

        offertaRepository.save(formOfferta);

        return "redirect:/pizze/" + id;
    }

    @GetMapping("/modifica/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        Offerta offerta = offertaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offerta non trovata"));
        Pizza pizza = offerta.getPizza(); // relazione bidirezionale

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");

        model.addAttribute("offerta", offerta);
        model.addAttribute("pizza", pizza);
        model.addAttribute("dataInizioFormattata", offerta.getDataInizio().format(formatter));
        model.addAttribute("dataFineFormattata", offerta.getDataFine().format(formatter));

        return "/offerte/modifica";
    }

    @PostMapping("/modifica/{id}")
    public String update(@PathVariable("id") Integer id,
            @ModelAttribute("offerta") Offerta offertaAggiornata) {
        Offerta offertaEsistente = offertaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Offerta non trovata"));

        // aggiorna solo i campi modificabili
        offertaEsistente.setTitolo(offertaAggiornata.getTitolo());
        offertaEsistente.setDataInizio(offertaAggiornata.getDataInizio());
        offertaEsistente.setDataFine(offertaAggiornata.getDataFine());

        offertaRepository.save(offertaEsistente);

        return "redirect:/pizze/" + offertaEsistente.getPizza().getId();
    }

    @PostMapping("/elimina/{id}")
    public String delete(@PathVariable("id") Integer id) {

        offertaRepository.deleteById(id);

        return "redirect:/pizze";
    }

    @PostMapping("/elimina-multiple")
    public String deleteMultiple(@RequestParam("selectedIds") List<Integer> ids) {

        for (Integer id : ids) {
            Optional<Offerta> optionalOfferta = offertaRepository.findById(id);

            if (optionalOfferta.isPresent()) {
                Offerta offerta = optionalOfferta.get();

                Pizza pizza = offerta.getPizza();
                if (pizza != null) {
                    pizza.getOfferte().remove(offerta);
                }

                offertaRepository.deleteById(id);
            }
        }

        return "redirect:/offerte";
    }

}
