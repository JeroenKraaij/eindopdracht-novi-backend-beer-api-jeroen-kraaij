package com.backend.beer_api_application.dto.output;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CategoryOutputDto {

    private Long id;
    private String beerCategoryName;
    private String beerCategoryType;
    private String beerCategoryDescription;
    private List<BeerOutputDto> beerOutputDtoList = new ArrayList<>();

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getBeerCategoryName() {
//        return beerCategoryName;
//    }
//
//    public void setBeerCategoryName(String beerCategoryName) {
//        this.beerCategoryName = beerCategoryName;
//    }
//
//    public List<BeerDto> getBeerDtoList() {
//        return Collections.unmodifiableList(beerDtoList);
//    }
//
//    public void setBeerDtoList(List<BeerDto> beerDtoList) {
//        this.beerDtoList = beerDtoList != null ? new ArrayList<>(beerDtoList) : new ArrayList<>();
//    }
//
//    public String getBeerCategoryType() {
//        return beerCategoryType;
//    }
//
//    public void setBeerCategoryType(String beerCategoryType) {
//        this.beerCategoryType = beerCategoryType;
//    }
//
//    public String getBeerCategoryDescription() {
//        return beerCategoryDescription;
//    }
//
//    public void setBeerCategoryDescription(String beerCategoryDescription) {
//        this.beerCategoryDescription = beerCategoryDescription;
//    }
}