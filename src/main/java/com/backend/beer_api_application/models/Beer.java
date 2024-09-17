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

    @Column(nullable = false)
    @Setter
    private String color;

    @Column(nullable = false)
    @Setter
    private String brewery;

    @Setter
    private String country;

    @Column(nullable = false)
    @Setter
    private Float abv;

    @Column(nullable = false)
    @Setter
    private Integer ibu;

    @Setter
    private String food;

    @Column(nullable = false)
    @Setter
    private String temperature;

    @Column(nullable = false)
    @Setter
    private String glassware;

    @Column(nullable = false)
    @Setter
    private String taste;

    @Column(nullable = false, precision = 10, scale = 2)
    @Setter
    private BigDecimal price;

    @Column(nullable = false)
    @Setter
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "beer_category", nullable = false)
    @Setter
    private Category category;

    public String getBeerCategoryType() {
        return category != null ? category.getBeerCategoryType() : null;
    }

    @ManyToMany
    @JoinTable(name = "beer_taste")
    @Setter
    private List<Taste> tastes = new ArrayList<>();

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    public Beer() {}

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setBeer(this);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
        orderLine.setBeer(null);
    }
}
