package com.backend.beer_api_application.dto.output;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class OrderOutputDto {

    private Long id;  // Order ID for reference

    // Customer details
    private Long customerId;  // Adding customer ID
    private String customerName;
    private String customerAddress;
    private String customerHouseNumber;
    private String customerZipcode;
    private String customerCity;
    private String customerEmail;

    // Order details
    private LocalDateTime orderDate;  // Date when the order was placed
    private String orderStatus;       // Current status of the order

    // Delivery details
    private String deliveryAddress;   // Address where the order will be delivered
    private String deliveryStatus;    // Optional field to track delivery status (e.g., PENDING, SHIPPED, DELIVERED)
    private LocalDateTime estimatedDeliveryTime;  // Optional estimated delivery time

    // Payment details
    private String paymentMethod;     // Payment method used for the order (e.g., CREDIT_CARD, PAYPAL)
    private String paymentStatus;     // Optional field to track the status of payment (e.g., PAID, PENDING, FAILED)

    // Price breakdown
    private BigDecimal totalAmountExcludingVat;  // Total price excluding VAT
    private BigDecimal totalAmountIncludingVat;  // Total price including VAT
    private BigDecimal vatAmount;  // Total VAT amount for the order

    // List of OrderLineOutputDto
    private List<OrderLineOutputDto> orderLines;  // List of all order lines
}
