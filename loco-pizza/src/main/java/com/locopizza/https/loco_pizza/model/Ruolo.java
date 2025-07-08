package com.locopizza.https.loco_pizza.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Ruolo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String nome;


    @ManyToMany(mappedBy = "ruoli" , fetch = FetchType.EAGER)
    private Set<Utente> utenti = new HashSet<>();


    public Set<Utente> getUtenti() {
        return this.utenti;
    }

    public void setUtenti(Set<Utente> utenti) {
        this.utenti = utenti;
    }



    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    
}
