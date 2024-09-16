package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "beers")
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

    // Many-to-One relationship with Category
    @ManyToOne
    @JoinColumn(name = "beer_category", nullable = false)
    @Setter
    private Category category;

    public String getBeerCategoryType() {
        return category != null ? category.getBeerCategoryType() : null;
    }

    // Many-to-Many relationship with Tastes
    @ManyToMany
    @JoinTable(name = "beer_taste")
    @Setter
    private List<Taste> tastes = new ArrayList<>();

    // One-to-many relationship with OrderLine
    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    // Default constructor
    public Beer() {}

    // Helper methods to manage the order lines
    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setBeer(this);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
        orderLine.setBeer(null);
    }
}
