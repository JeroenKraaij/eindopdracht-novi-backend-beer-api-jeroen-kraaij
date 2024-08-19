package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Setter
    private String name;

    @Column(nullable = false)
    @Setter
    private String brand;

    @Column(length = 1000, nullable = false)
    @Setter
    private String description;

    @Setter
    private String color;

    @Setter
    private String brewery;

    @Setter
    private String country;

    @Column(nullable = false)
    @Setter
    private Float abv;

    @Setter
    private Integer ibu;

    @Setter
    private String food;

    @Setter
    private String temperature;

    @Setter
    private String glassware;

    @Setter
    private String taste;

    @Column(nullable = false)
    @Setter
    private BigDecimal price;

    @Setter
    private String imageUrl;

    @ManyToOne
    @JoinColumn(nullable = false)
    @Setter
    private Category category;

    @ManyToMany(mappedBy = "beer", cascade = CascadeType.ALL)
    private List<Order> orders;

    // Default constructor
    public Beer() {
    }
}