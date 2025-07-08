package com.locopizza.https.loco_pizza.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.locopizza.https.loco_pizza.model.Ruolo;
import com.locopizza.https.loco_pizza.model.Utente;

public class DatabaseUserDetails implements UserDetails {
    private final Integer id;
    private final String username;
    private final String password;
    private final String nome;
    private final String cognome;
    private final String eMail;
    private final Set<GrantedAuthority> authorities;

    public DatabaseUserDetails(Utente utente) {
        this.id = utente.getId();
        this.username = utente.getUsername();
        this.password = utente.getPassword();
        this.nome = utente.getNome();
        this.cognome = utente.getCognome();
        this.eMail = utente.getEMail();

        this.authorities = new HashSet<>();
        for (Ruolo ruolo : utente.getRuoli()) {
            String nomeRuolo = ruolo.getNome();
            
            this.authorities.add(new SimpleGrantedAuthority(nomeRuolo));
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    public Integer getId() {
        return this.id;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    public String getNome() {
        return this.nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public String geteMail() {
        return this.eMail;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}


    
