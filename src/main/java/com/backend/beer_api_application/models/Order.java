package com.backend.beer_api_application.models;

import com.backend.beer_api_application.enums.OrderStatus;
import com.backend.beer_api_application.enums.PaymentMethod;
import com.backend.beer_api_application.utils.AddressFormatter;

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

    // Use PaymentMethod Enum instead of String
    @Enumerated(EnumType.STRING)
    @Setter
    private PaymentMethod paymentMethod;

    // Calculated fields for total amounts
    @Setter
    private BigDecimal totalAmountExcludingVat;
    @Setter
    private BigDecimal totalAmountIncludingVat;

    // Default constructor
    public Order() {
        this.orderStatus = OrderStatus.PENDING;
        this.orderDate = LocalDateTime.now();    // Set current date as order date
    }

    // Set delivery address from customer details
    public void setDeliveryAddress() {
        if (this.customer != null) {
            this.deliveryAddress = AddressFormatter.formatAddress(
                    customer.getAddress(),
                    customer.getHouseNumber(),
                    customer.getZipcode(),
                    customer.getCity()
            );
        }
    }

}
