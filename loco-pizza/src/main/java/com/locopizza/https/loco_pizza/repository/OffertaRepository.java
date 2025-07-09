package com.locopizza.https.loco_pizza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locopizza.https.loco_pizza.model.Offerta;

public interface OffertaRepository extends JpaRepository<Offerta, Integer>{
    List<Offerta> findByTitoloContainingIgnoreCase(String keyword);
}
