package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String beerCategoryName;

    private String beerCategoryType;

    private String beerCategoryDescription;

    @OneToMany
    private Set<Beer> beers = new HashSet<>();

    // No-argument constructor
    public Category() {
    }

    public Category(Long id, String beerCategoryName, String beerCategoryType, String beerCategoryDescription) {
        this.id = id;
        this.beerCategoryName = beerCategoryName;
        this.beerCategoryType = beerCategoryType;
        this.beerCategoryDescription = beerCategoryDescription;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeerCategoryName() {
        return beerCategoryName;
    }

    public void setBeerCategoryName(String categoryName) {
        this.beerCategoryName = categoryName;
    }

    public Set<Beer> getBeers() {
        return beers;
    }

    public void setBeers(Set<Beer> beers) {
        this.beers = beers;
    }

    public String getBeerCategoryType() {
        return beerCategoryType;
    }

    public void setBeerCategoryType(String beerCategoryType) {
        this.beerCategoryType = beerCategoryType;
    }

    public String getBeerCategoryDescription() {
        return beerCategoryDescription;
    }

    public void setBeerCategoryDescription(String beerCategoryDescription) {
        this.beerCategoryDescription = beerCategoryDescription;
    }
}
