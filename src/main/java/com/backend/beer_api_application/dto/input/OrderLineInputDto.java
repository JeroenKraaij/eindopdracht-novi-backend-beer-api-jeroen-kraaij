package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineInputDto {

    @NotNull
    private Long beerId;

    @NotNull
    @Min(1)
    private Integer quantity;

    @NotNull
    private BigDecimal priceAtPurchase;
}