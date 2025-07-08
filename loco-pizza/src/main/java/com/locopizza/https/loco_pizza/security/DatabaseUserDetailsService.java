package com.locopizza.https.loco_pizza.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.locopizza.https.loco_pizza.model.Utente;
import com.locopizza.https.loco_pizza.repository.UtenteRepository;

@Service
public class DatabaseUserDetailsService implements UserDetailsService{
    

    @Autowired
    UtenteRepository utenteRepository;



    @Override
    public UserDetails loadUserByUsername (String username) throws UsernameNotFoundException {
        Optional <Utente> utente = utenteRepository.findByUsername(username);

        if (utente.isPresent()) {
            return new DatabaseUserDetails(utente.get());
            
        } else{
            throw new UsernameNotFoundException("Utente non trovato");
        }
    }
}
