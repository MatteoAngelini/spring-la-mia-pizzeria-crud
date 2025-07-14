package com.locopizza.https.loco_pizza.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locopizza.https.loco_pizza.model.Utente;


public interface UtenteRepository extends JpaRepository<Utente, Integer>{

    Optional <Utente> findByUsername(String username);

    List<Utente> findByNomeContainingIgnoreCase(String keyword);
    
} 
