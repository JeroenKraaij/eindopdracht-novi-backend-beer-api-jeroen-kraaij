package com.backend.beer_api_application.models;

import com.backend.beer_api_application.emum.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship with Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @Setter
    private Customer customer;

    // One-to-many relationship with OrderLine
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    // Enum for order status
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Setter
    private OrderStatus orderStatus;

    // Order date
    @Column(nullable = false)
    private LocalDateTime orderDate;

    // Delivery address and payment method
    @Setter
    private String deliveryAddress;

    @Setter
    private String paymentMethod;

    // Calculated fields for total amounts
    private BigDecimal totalAmountExcludingVat;
    private BigDecimal totalAmountIncludingVat;


    public Order() {
        this.orderStatus = OrderStatus.PENDING;  // Default status
        this.orderDate = LocalDateTime.now();    // Set current date as order date
    }

    // Helper methods
    public void addOrderLine(OrderLine orderLine) {
        if (orderLine != null) {
            orderLines.add(orderLine);
            orderLine.setOrder(this);
        }
    }

    public void removeOrderLine(OrderLine orderLine) {
        if (orderLine != null) {
            orderLines.remove(orderLine);
            orderLine.setOrder(null);
        }
    }

    // Helper method to calculate total amounts from OrderLines
    public void calculateTotalAmounts() {
        BigDecimal totalExclVat = BigDecimal.ZERO;
        BigDecimal totalInclVat = BigDecimal.ZERO;

        for (OrderLine orderLine : orderLines) {
            totalExclVat = totalExclVat.add(orderLine.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderLine.getAmount())));
            totalInclVat = totalExclVat.add(orderLine.getPriceAtPurchase().multiply(BigDecimal.valueOf(orderLine.getAmount())).multiply(BigDecimal.valueOf(1.21)));
        }

        this.totalAmountExcludingVat = totalExclVat;
        this.totalAmountIncludingVat = totalInclVat;
    }
}
