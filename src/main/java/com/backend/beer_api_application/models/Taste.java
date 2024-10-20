package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class Taste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tastes")
    private List<Beer> beers = new ArrayList<>();

    // Default constructor
    public Taste() {}

    // Constructor with name
    public Taste(String name) {
        this.name = name;
    }

    // Helper methods to manage beers
    public void addBeer(Beer beer) {
        beers.add(beer);
        beer.getTastes().add(this);
    }

    public void removeBeer(Beer beer) {
        beers.remove(beer);
        beer.getTastes().remove(this);
    }
}