package com.locopizza.https.loco_pizza.controller;

import java.util.List;
import java.util.Optional;

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

import com.locopizza.https.loco_pizza.model.Ingrediente;
import com.locopizza.https.loco_pizza.model.Notifica;
import com.locopizza.https.loco_pizza.model.Pizza;
import com.locopizza.https.loco_pizza.repository.IngredienteRepository;
import com.locopizza.https.loco_pizza.repository.NotificaRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/ingredienti")
public class IngredienteController {

    @Autowired
    private IngredienteRepository ingredienteRepository;

    @Autowired
    private NotificaRepository notificaRepository;

    @GetMapping
    public String index(@RequestParam(value = "keyword", required = false) String keyword, Model model) {
        List<Ingrediente> ingredienti;
        if (keyword != null && !keyword.isEmpty()) {
            ingredienti = ingredienteRepository.findByNomeContainingIgnoreCase(keyword);
        } else {
            ingredienti = ingredienteRepository.findAll();
        }
        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);
        model.addAttribute("ingredienti", ingredienti);
        model.addAttribute("keyword", keyword);
        return "ingredienti/index";
    }

    @GetMapping("/creazione")
    public String create(Model model) {
        List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
        long nonLette = notificaRepository.countByLettaFalse();

        model.addAttribute("notifiche", notifiche);
        model.addAttribute("nonLette", nonLette);

        model.addAttribute("ingrediente", new Ingrediente());
        return "/ingredienti/creazione";
    }

    @PostMapping("/creazione")
    public String store(@Valid @ModelAttribute("ingrediente") Ingrediente formIngrediente, BindingResult bindingResult,
            Model model,
            @RequestParam("azione") String azione) {

        if (azione.equals("cancel")) {
            List<Notifica> notifiche = notificaRepository.findTop5ByOrderByDataCreazioneDesc();
            long nonLette = notificaRepository.countByLettaFalse();

            model.addAttribute("notifiche", notifiche);
            model.addAttribute("nonLette", nonLette);

            return "/ingredienti/creazione";
        }
        if (bindingResult.hasErrors()) {
            return "/ingredienti/creazione";
        }

        ingredienteRepository.save(formIngrediente);

        return "redirect:/ingredienti";
    }

    @GetMapping("/modifica/{id}")
    public String modificaIngrediente(@PathVariable("id") Integer id, Model model) {
        Optional<Ingrediente> optionalIngrediente = ingredienteRepository.findById(id);
        if (optionalIngrediente.isPresent()) {
            model.addAttribute("ingrediente", optionalIngrediente.get());
            return "ingredienti/modifica";
        } else {
            return "redirect:/ingredienti";
        }
    }

    @PostMapping("/modifica/{id}")
    public String update(@PathVariable("id") Integer id, @Valid @ModelAttribute("pizza") Ingrediente formIngrediente,
            BindingResult bindingResult, Model model, @RequestParam("azione") String azione) {

        if (azione.equals("cancel")) {

            return "/ingredienti/modifica";
        }
        if (bindingResult.hasErrors()) {

            return "/ingredienti/modifica";
        }

        ingredienteRepository.save(formIngrediente);

        return "redirect:/ingredienti";
    }

    @PostMapping("/elimina/{id}")
    public String delete(@PathVariable("id") Integer id) {

        Ingrediente ingredienteDaCancellare = ingredienteRepository.findById(id).get();
        for (Pizza pizza : ingredienteDaCancellare.getPizze()) {
            pizza.getIngredienti().remove(ingredienteDaCancellare);
        }

        ingredienteRepository.deleteById(id);

        return "redirect:/ingredienti";
    }

    @PostMapping("/elimina-multiple")
    public String deleteMultiple(@RequestParam("selectedIds") List<Integer> ids) {

        for (Integer id : ids) {
            Optional<Ingrediente> optionalIngrediente = ingredienteRepository.findById(id);

            if (optionalIngrediente.isPresent()) {
                Ingrediente ingrediente = optionalIngrediente.get();

                // Rimuovi l'ingrediente da ogni pizza
                for (Pizza pizza : ingrediente.getPizze()) {
                    pizza.getIngredienti().remove(ingrediente);
                }

                // Elimina l'ingrediente
                ingredienteRepository.deleteById(id);
            }
        }

        return "redirect:/ingredienti";
    }

}
