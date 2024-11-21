package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoryInputDto {

    @NotBlank
    @Size(max = 255)
    private String beerCategoryName;

    @Size(max = 1000)
    private String beerCategoryDescription;

    @Size(max = 255)
    private String beerCategoryType;
}