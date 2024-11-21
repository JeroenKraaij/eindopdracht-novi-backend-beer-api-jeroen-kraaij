package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class BeerInputDto {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 255)
    private String name;

    @NotBlank(message = "Brand is mandatory")
    @Size(max = 255)
    private String brand;

    @NotNull(message = "Category is mandatory")
    private Long category;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 1000)
    private String description;

    @Size(max = 50)
    private String color;

    @Size(max = 255)
    private String brewery;

    @Size(max = 255)
    private String country;

    @NotNull(message = "ABV is mandatory")
    @PositiveOrZero
    private Float abv;

    @NotNull(message = "IBU is mandatory")
    @PositiveOrZero
    private Integer ibu;

    @Size(max = 255)
    private String food;

    @Size(max = 50)
    private String temperature;

    @Size(max = 255)
    private String glassware;

    @Size(max = 255)
    private String taste;

    @NotNull(message = "Price is mandatory")
    @Positive
    private BigDecimal price;

    private MultipartFile imageFile;

    @NotNull(message = "Stock is mandatory")
    @PositiveOrZero
    private Integer inStock;
}