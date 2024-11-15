package com.backend.beer_api_application.dto.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineOutputDto {

    private Long id;
    private Long beerId;  // Beer ID for reference
    private String beerName;  // Name of the beer
    private String beerType;  // Type of the beer (optional for further beer categorization)
    private Integer amount;  // Quantity of the beer in this order line
    private BigDecimal priceAtPurchase;  // Unit price of the beer at the time of purchase
    private BigDecimal totalPriceExcludingVat;  // Total price excluding VAT
    private BigDecimal totalPriceIncludingVat;  // Total price including VAT
    private BigDecimal vatAmount;  // Amount of VAT

}
