package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String beerCategoryName;
    private String beerCategoryType;
    private String beerCategoryDescription;

    @OneToMany
    private Set<Beer> beers = new HashSet<>();

    // Default constructor
    public Category() {
    }
}