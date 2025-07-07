package com.locopizza.https.loco_pizza.model;

import java.util.List;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="pizzas")
public class Pizza {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;


    @NotBlank(message = "Il titolo è obbligatorio")
    @Size(max = 100, message = "Il titolo non può superare i 100 caratteri")
    @Column(name="title")
    private String titolo;



    @NotBlank(message = "La descrizione è obbligatoria")
    @Column(name="description")
    private String descrizione;
 
    @Lob
    @Column(name="image_url")
    private String imageUrl;


    @NotNull(message = "Il prezzo è obbligatorio")
    @Column(name="price")
    private Double prezzo;



    @OneToMany(mappedBy = "pizza")
    private List <Offerta> offerte;

     //Get + Set Offerta
    public List<Offerta> getOfferte() {
        return this.offerte;
    }

    public void setOfferte(List<Offerta> offerte) {
        this.offerte = offerte;
    }

    @ManyToMany()
    @JoinTable(name = "pizza_ingredients",
            joinColumns = @jakarta.persistence.JoinColumn(name = "pizza_id"),
            inverseJoinColumns = @jakarta.persistence.JoinColumn(name = "ingredient_id"))
    private Set<Ingrediente> ingredienti;

    //Get + Set Ingrediente

    public Set<Ingrediente> getIngredienti() {
        return this.ingredienti;
    }

    public void setIngredienti(Set<Ingrediente> ingredienti) {
        this.ingredienti = ingredienti;
    }




    //Get + Set

    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitolo() {
        return this.titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDescrizione() {
        return this.descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Double getPrezzo() {
        return this.prezzo;
    }

    public void setPrezzo(Double prezzo) {
        this.prezzo = prezzo;
    }

    
    //toString

    @Override
    public String toString() {
        return "";
    }

}
