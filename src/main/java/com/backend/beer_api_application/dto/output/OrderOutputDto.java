package com.backend.beer_api_application.dto.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderOutputDto {

    private Long id;
    private String customerName;
    private String customerAddress;
    private String customerHouseNumber;
    private String customerZipcode;
    private String customerCity;
    private String customerEmail;
    private LocalDateTime orderDate;
    private String orderStatus;
    private BigDecimal totalAmountExcludingVat;
    private BigDecimal totalAmountIncludingVat;
    private List<OrderLineOutputDto> orderLines;
    private String deliveryAddress;
    private String paymentMethod;
}
