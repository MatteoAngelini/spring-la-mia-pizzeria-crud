package com.locopizza.https.loco_pizza.model;


import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name = "ingredients")
public class Ingrediente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "Il nome dell'ingrediente non può essere vuoto")
    @Column(name ="name")
    private String nome;

    @NotBlank(message = "La descrizione dell'ingrediente non può essere vuota")
    @Lob
    @Column(name = "description")
    private String descrizione;


    @ManyToMany(mappedBy = "ingredienti")
    private Set<Pizza> pizze;


    public Set<Pizza> getPizze() {
        return this.pizze;
    }

    public void setPizze(Set<Pizza> pizze) {
        this.pizze = pizze;
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

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
    
}
