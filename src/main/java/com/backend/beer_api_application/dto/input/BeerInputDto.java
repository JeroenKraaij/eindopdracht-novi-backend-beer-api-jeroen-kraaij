package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;

@Data
public class BeerInputDto {

    @NotBlank(message = "Name is mandatory")
    @Size(max = 100, message = "Name can be at most 100 characters")
    private String name;

    @NotBlank(message = "Brand is mandatory")
    @Size(max = 100, message = "Brand can be at most 100 characters")
    private String brand;

    @NotNull(message = "Category is mandatory")
    private Long category;

    @NotBlank(message = "Description is mandatory")
    @Size(max = 1000, message = "Description can be at most 1000 characters")
    private String description;

    @Size(max = 50, message = "Color can be at most 50 characters")
    private String color;

    @Size(max = 100, message = "Brewery can be at most 100 characters")
    private String brewery;

    @Size(max = 100, message = "Country can be at most 100 characters")
    private String country;

    @NotNull(message = "ABV is mandatory")
    @PositiveOrZero(message = "ABV must be zero or positive")
    private Float abv;

    @NotNull(message = "IBU is mandatory")
    @PositiveOrZero(message = "IBU must be zero or positive")
    private Integer ibu;

    @Size(max = 255, message = "Food can be at most 255 characters")
    private String food;

    @Size(max = 50, message = "Temperature can be at most 50 characters")
    private String temperature;

    @Size(max = 50, message = "Glassware can be at most 50 characters")
    private String glassware;

    @Size(max = 255, message = "Taste can be at most 255 characters")
    private String taste;

    @NotNull(message = "Price is mandatory")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Image is mandatory")
    private MultipartFile imageFile;

    @NotNull(message = "Stock is mandatory")
    @PositiveOrZero(message = "Stock must be zero or positive")
    private Integer inStock;
}
