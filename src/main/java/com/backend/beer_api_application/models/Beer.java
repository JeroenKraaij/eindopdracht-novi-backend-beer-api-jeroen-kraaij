package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "beers")
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Column(length = 1000, nullable = false)
    private String description;

    @Column(nullable = false)
    private String color;

    @Column(nullable = false)
    private String brewery;

    private String country;

    @Column(nullable = false)
    private Float abv;

    @Column(nullable = false)
    private Integer ibu;

    private String food;

    @Column(nullable = false)
    private String temperature;

    @Column(nullable = false)
    private String glassware;

    @Column(nullable = false)
    private String taste;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @ManyToOne
    @JoinColumn( nullable = false)
    private Category category;

    @ManyToMany
    @JoinTable
    private List<Taste> tastes = new ArrayList<>();

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ImageUpload> imageUploads = new ArrayList<>();

    @Column(nullable = false)
    private Integer inStock = 0;

    public String getBeerCategoryType() {
        return category != null ? category.getBeerCategoryType() : null;
    }

    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setBeer(this);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
        orderLine.setBeer(null);
    }

    public void incrementStock(int amount) {
        this.inStock += amount;
    }

    public void decrementStock(int amount) {
        if (this.inStock - amount >= 0) {
            this.inStock -= amount;
        } else {
            throw new IllegalArgumentException("Insufficient stock");
        }
    }

    public void addImage(ImageUpload imageUpload) {
        imageUploads.add(imageUpload);
        imageUpload.setBeer(this);
    }

    public void removeImage(ImageUpload imageUpload) {
        imageUploads.remove(imageUpload);
        imageUpload.setBeer(null);
    }
}