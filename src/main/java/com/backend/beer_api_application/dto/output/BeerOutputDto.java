package com.backend.beer_api_application.dto.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class BeerOutputDto {

    private Long id;
    private String name;
    private String brand;
    private String category;
    private String description;
    private String color;
    private String brewery;
    private String country;
    private String abv;
    private Integer ibu;
    private String food;
    private String temperature;
    private String glassware;
    private String taste;
    private BigDecimal price;
    private String imageUrl;
}


