package com.backend.beer_api_application.dto.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineOutputDto {

    private Long id;
    private String beerName;  // Name of the beer
    private Integer amount;  // Quantity of the beer ordered
    private BigDecimal priceAtPurchase;  // Price at the time of the order
    private BigDecimal totalPriceExcludingVat;  // Total price excluding VAT
    private BigDecimal totalPriceIncludingVat;  // Total price including VAT
}