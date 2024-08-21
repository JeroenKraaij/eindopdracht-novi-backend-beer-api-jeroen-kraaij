package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineInputDto {

    @NotNull(message = "Beer ID is mandatory")
    private Long beerId;  // ID of the beer being ordered

    @NotNull(message = "Amount is mandatory")
    @Min(value = 1, message = "Amount must be at least 1")
    private Integer amount;  // Quantity of the beer ordered

    @NotNull(message = "Price at purchase is mandatory")
    private BigDecimal priceAtPurchase;  // Price at the time of the order
}
