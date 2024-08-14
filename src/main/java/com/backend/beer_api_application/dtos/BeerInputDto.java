package com.backend.beer_api_application.dtos;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public class BeerInputDto {

    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Brand is mandatory")
    private String brand;

    @NotBlank(message = "Category is mandatory")
    private Long category;

    private String description;
    private String color;
    private String brewery;
    private String country;

    @NotNull(message = "ABV is mandatory")
    @PositiveOrZero(message = "ABV must be zero or positive")
    private Float abv;

    @NotNull(message = "IBU is mandatory")
    @PositiveOrZero(message = "IBU must be zero or positive")
    private Integer ibu;

    private String food;
    private String temperature;
    private String glassware;
    private String taste;

    @NotNull(message = "Price is mandatory")
    @PositiveOrZero(message = "Price must be zero or positive")
    private BigDecimal price;

    private String imageUrl;

    // Getters and Setters
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

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
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

}
