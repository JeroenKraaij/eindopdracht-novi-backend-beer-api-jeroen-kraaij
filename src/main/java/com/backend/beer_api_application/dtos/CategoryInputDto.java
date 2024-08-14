package com.backend.beer_api_application.dtos;

public class CategoryInputDto {

    private String beerCategoryName;
    private String beerCategoryDescription;
    private String beerCategoryType;

    public String getBeerCategoryName() {
        return beerCategoryName;
    }

    public void setBeerCategoryName(String beerCategoryName) {
        this.beerCategoryName = beerCategoryName;
    }

    public String getBeerCategoryDescription() {
        return beerCategoryDescription;
    }

    public void setBeerCategoryDescription(String beerCategoryDescription) {
        this.beerCategoryDescription = beerCategoryDescription;
    }

    public String getBeerCategoryType() {
        return beerCategoryType;
    }

    public void setBeerCategoryType(String beerCategoryType) {
        this.beerCategoryType = beerCategoryType;
    }
}

