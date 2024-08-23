package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship with Beer
    @ManyToOne
    @JoinColumn(name = "beer_id", nullable = false)
    @Setter
    private Beer beer;

    // Many-to-one relationship with Order
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @Setter
    private Order order;

    @Column(nullable = false)
    @Setter
    private Integer amount;

    @Column(nullable = false)
    @Setter
    private BigDecimal priceAtPurchase;

    // VAT constant (21%)
    private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.21);

    // Calculate total price excluding VAT
    public BigDecimal getTotalPriceExcludingVat() {
        return priceAtPurchase.multiply(BigDecimal.valueOf(amount));
    }

    // Calculate total price including VAT
    public BigDecimal getTotalPriceIncludingVat() {
        BigDecimal totalPriceExcludingVat = getTotalPriceExcludingVat();
        return totalPriceExcludingVat.add(totalPriceExcludingVat.multiply(VAT_RATE).setScale(2, RoundingMode.HALF_UP));
    }


    // Default constructor
    public OrderLine() {}
}
