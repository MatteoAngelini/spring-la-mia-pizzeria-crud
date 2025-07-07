package com.locopizza.https.loco_pizza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locopizza.https.loco_pizza.model.Ingrediente;


public interface IngredienteRepository extends JpaRepository<Ingrediente, Integer> {
    
    List<Ingrediente> findByNomeContainingIgnoreCase(String keyword);
   
    
}
