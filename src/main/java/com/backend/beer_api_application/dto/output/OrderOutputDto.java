package com.backend.beer_api_application.dto.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderOutputDto {

    private Long id;
    private Long customerId;
    private String customerName;
    private String customerAddress;
    private String customerHouseNumber;
    private String customerZipcode;
    private String customerCity;
    private String customerEmail;
    private LocalDateTime orderDate;
    private String orderStatus;
    private String deliveryAddress;
    private String paymentMethod;
    private BigDecimal totalAmountExcludingVat;
    private BigDecimal totalAmountIncludingVat;
    private BigDecimal vatAmount;
    private List<OrderLineOutputDto> orderLines;
}
