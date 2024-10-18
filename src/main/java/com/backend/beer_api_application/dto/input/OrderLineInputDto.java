package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineInputDto {

    @NotNull(message = "Beer ID is mandatory")
    private Long beerId;  // ID of the beer being ordered

    @NotNull(message = "Quantity is mandatory")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;  // Number of units ordered

    @NotNull(message = "Price at purchase is mandatory")
    private BigDecimal priceAtPurchase;  // Price at the time of purchase
}
