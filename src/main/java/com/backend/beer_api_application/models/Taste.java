package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "taste")
public class Taste {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @ManyToMany(mappedBy = "tastes")
    private List<Beer> beers = new ArrayList<>();
    public Taste() {}
    public Taste(String name) {
        this.name = name;
    }

}