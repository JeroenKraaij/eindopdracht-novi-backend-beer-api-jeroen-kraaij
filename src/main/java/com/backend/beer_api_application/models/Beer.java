package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Entity
public class Beer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String brand;

    @Column(length = 1000, nullable = false)
    private String description;

    private String color;
    private String brewery;
    private String country;
    private Float abv;
    private Integer ibu;
    private String food;
    private String temperature;
    private String glassware;
    private String taste;
    private BigDecimal price;
    private String imageUrl;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Category category;

    @OneToMany(mappedBy = "beer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    // Default constructor
    public Beer() {}

    // All-args constructor
    public Beer(Long id, String name, String brand, String description, String color, String brewery, String country, Float abv, Integer ibu, String food, String temperature, String glassware, String taste, BigDecimal price, String imageUrl, Category category ) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.description = description;
        this.color = color;
        this.brewery = brewery;
        this.country = country;
        this.abv = abv;
        this.ibu = ibu;
        this.food = food;
        this.temperature = temperature;
        this.glassware = glassware;
        this.taste = taste;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getBrewery() {
        return brewery;
    }

    public void setBrewery(String brewery) {
        this.brewery = brewery;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Float getAbv() {
        return abv;
    }

    public void setAbv(Float abv) {
        this.abv = abv;
    }

    public Integer getIbu() {
        return ibu;
    }

    public void setIbu(Integer ibu) {
        this.ibu = ibu;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public String getTemperature() {
        return temperature;
    }

    public void setTemperature(String temperature) {
        this.temperature = temperature;
    }

    public String getGlassware() {
        return glassware;
    }

    public void setGlassware(String glassware) {
        this.glassware = glassware;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    // Equals & HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Beer beer = (Beer) o;
        return Objects.equals(id, beer.id) &&
                Objects.equals(name, beer.name) &&
                Objects.equals(brand, beer.brand) &&
                Objects.equals(category, beer.category) &&
                Objects.equals(description, beer.description) &&
                Objects.equals(color, beer.color) &&
                Objects.equals(brewery, beer.brewery) &&
                Objects.equals(country, beer.country) &&
                Objects.equals(abv, beer.abv) &&
                Objects.equals(ibu, beer.ibu) &&
                Objects.equals(food, beer.food) &&
                Objects.equals(temperature, beer.temperature) &&
                Objects.equals(glassware, beer.glassware) &&
                Objects.equals(taste, beer.taste) &&
                Objects.equals(price, beer.price) &&
                Objects.equals(imageUrl, beer.imageUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, brand, category, description, color, brewery, country, abv, ibu, food, temperature, glassware, taste, price, imageUrl);
    }

    // toString
    @Override
    public String toString() {
        return "Beer{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", description='" + description + '\'' +
                ", color='" + color + '\'' +
                ", brewery='" + brewery + '\'' +
                ", country='" + country + '\'' +
                ", abv=" + abv +
                ", ibu=" + ibu +
                ", food='" + food + '\'' +
                ", temperature='" + temperature + '\'' +
                ", glassware='" + glassware + '\'' +
                ", taste='" + taste + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", category=" + category +
                '}';
    }
}
