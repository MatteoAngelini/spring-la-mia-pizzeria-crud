package com.locopizza.https.loco_pizza.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.locopizza.https.loco_pizza.model.Pizza;

public interface PizzaRepository extends JpaRepository<Pizza, Integer>{
    List<Pizza> findByTitoloContainingIgnoreCase(String keyword);
    Page<Pizza> findByTitoloContainingIgnoreCase(String keyword, Pageable pageable);

}
