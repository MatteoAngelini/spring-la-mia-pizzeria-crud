package com.locopizza.https.loco_pizza.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.locopizza.https.loco_pizza.model.Notifica;

public interface NotificaRepository extends JpaRepository<Notifica, Integer> {
    List<Notifica> findTop5ByOrderByDataCreazioneDesc();
    long countByLettaFalse();
    List<Notifica> findByLettaFalse();
}
