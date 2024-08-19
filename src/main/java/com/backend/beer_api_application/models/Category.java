package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String beerCategoryName;

    @Setter
    private String beerCategoryType;

    @Setter
    private String beerCategoryDescription;

    @OneToMany
    @Setter
    private Set<Beer> beers = new HashSet<>();

    // Default constructor
    public Category() {
    }
}
