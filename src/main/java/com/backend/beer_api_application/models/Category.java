package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@Table(name = "categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = false)
    private String beerCategoryName;
    private String beerCategoryType;
    private String beerCategoryDescription;

    // Properly mapped relationship with Beer entity
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude  // To avoid recursive calls during equality checks
    @ToString.Exclude           // To avoid recursive toString calls, which can lead to stack overflow
    private Set<Beer> beers = new HashSet<>();

    // Default constructor
    public Category() {
    }
}
