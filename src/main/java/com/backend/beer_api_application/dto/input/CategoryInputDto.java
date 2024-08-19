package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CategoryInputDto {

    @NotBlank(message = "Beer category name is mandatory")
    @Size(max = 200, message = "Beer category name can be at most 200 characters")
    private String beerCategoryName;

    @Size(max = 1000, message = "Beer category description can be at most 1000 characters")
    private String beerCategoryDescription;

    @NotBlank(message = "Beer category type is mandatory")
    @Size(max = 200, message = "Beer category type can be at most 200 characters")
    private String beerCategoryType;
}
