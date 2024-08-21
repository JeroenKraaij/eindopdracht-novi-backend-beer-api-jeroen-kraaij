package com.backend.beer_api_application.dto.output;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderOutputDto {

    private Long id;  // Order ID

    private String customerName;  // Name of the customer (e.g., "John Doe")

    private String customerAddress;  // Customer's address

    private String customerHouseNumber;  // Customer's house number

    private String customerZipcode;  // Customer's zip code

    private String customerCity;  // Customer's city

    private String customerEmail;  // Customer's email address

    private LocalDateTime orderDate;  // Date and time when the order was placed

    private String orderStatus;  // Status of the order (e.g., "Pending", "Completed", "Cancelled")

    private BigDecimal totalAmountExcludingVat;  // Total amount for the order excluding VAT

    private BigDecimal totalAmountIncludingVat;  // Total amount for the order including VAT

    private List<OrderLineOutputDto> orderLines;  // List of order line details

    private String deliveryAddress;  // Delivery address for the order

    private String paymentMethod;  // Payment method used (e.g., "Credit Card", "PayPal")
}
