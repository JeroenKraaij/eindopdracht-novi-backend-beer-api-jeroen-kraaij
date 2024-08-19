package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineInputDto {

    @NotNull(message = "Beer ID is mandatory")
    private Long beerId;  // ID of the beer being ordered

    @NotNull(message = "Amount is mandatory")
    @Positive(message = "Amount must be positive")
    private Integer amount;  // Quantity of the beer being ordered

    @NotNull(message = "Price at purchase is mandatory")
    @Positive(message = "Price at purchase must be positive")
    private BigDecimal priceAtPurchase;  // Price at the time of the order
}
