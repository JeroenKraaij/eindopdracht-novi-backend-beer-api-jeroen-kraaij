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
    private LocalDateTime orderDate;  // Added order date field
    private String orderStatus;       // Order status as a string
    private BigDecimal totalAmountExcludingVat;  // Total price excluding VAT
    private BigDecimal totalAmountIncludingVat;  // Total price including VAT
    private List<OrderLineOutputDto> orderLines;  // List of OrderLineOutputDto
    private String deliveryAddress;   // Optional field for delivery address
    private String paymentMethod;     // Optional field for payment method
}
