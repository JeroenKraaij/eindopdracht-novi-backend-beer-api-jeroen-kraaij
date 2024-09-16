package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineInputDto {

    @NotNull(message = "Beer ID is mandatory")
    private Long beerId;

    @NotNull(message = "Quantity is mandatory")
    private Integer quantity;

    @NotNull(message = "Price is mandatory")
    private BigDecimal price;
}
