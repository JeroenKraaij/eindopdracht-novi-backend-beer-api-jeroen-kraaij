package com.backend.beer_api_application.dto.output;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderLineOutputDto {

    private Long id;
    private Long beerId;
    private String beerName;
    private String beerType;
    private Integer amount;
    private BigDecimal priceAtPurchase;
    private BigDecimal totalPriceExcludingVat;
    private BigDecimal totalPriceIncludingVat;
    private BigDecimal vatAmount;

}
