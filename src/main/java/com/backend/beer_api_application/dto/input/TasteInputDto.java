package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TasteInputDto {
    @NotNull
    private String name;
}