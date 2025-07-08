package com.locopizza.https.loco_pizza.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank
    private String eMail;

    @NotBlank
    private String nome;

    @NotBlank
    private String cognome;

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "utente_ruolo",
        joinColumns = @JoinColumn(name = "utente_id"),
        inverseJoinColumns = @JoinColumn(name = "ruolo_id")
    )
    private Set<Ruolo> ruoli = new HashSet<>();

    


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEMail() {
        return this.eMail;
    }

    public void setEMail(String eMail) {
        this.eMail = eMail;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return this.cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Ruolo> getRuoli() {
        return this.ruoli;
    }

    public void setRuoli(Set<Ruolo> ruoli) {
        this.ruoli = ruoli;
    }
    
}
