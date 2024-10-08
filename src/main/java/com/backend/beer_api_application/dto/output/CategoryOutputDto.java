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
}