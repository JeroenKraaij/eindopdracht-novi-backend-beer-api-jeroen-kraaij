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
@Setter
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn( nullable = false)
    private Customer customer;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderLine> orderLines = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStatus orderStatus = OrderStatus.PENDING;

    @Column(nullable = false)
    private LocalDateTime orderDate = LocalDateTime.now();

    private String deliveryAddress;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private BigDecimal totalAmountExcludingVat;
    private BigDecimal totalAmountIncludingVat;

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