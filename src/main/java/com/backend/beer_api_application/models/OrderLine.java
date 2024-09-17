package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Getter
@Table(name = "orderlines")
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

    // Quantity of beers in the order
    @Column(nullable = false)
    @Setter
    private Integer amount;

    // Price at the time of purchase
    @Column(nullable = false, precision = 10, scale = 2)
    @Setter
    private BigDecimal priceAtPurchase;

    // VAT constant (21%)
    private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.21);

    // Constructor for initialization - priceAtPurchase is set to the current price of the beer
    public OrderLine(Beer beer, Integer amount) {
        this.beer = beer;
        this.amount = amount;
        this.priceAtPurchase = beer.getPrice(); // Get the current price from the Beer entity
    }

    // Default constructor
    public OrderLine() {}

    // Calculate total price excluding VAT
    public BigDecimal getTotalPriceExcludingVat() {
        return priceAtPurchase.multiply(BigDecimal.valueOf(amount))
                .setScale(2, RoundingMode.HALF_UP);
    }

    // Calculate total price including VAT
    public BigDecimal getTotalPriceIncludingVat() {
        BigDecimal totalPriceExcludingVat = getTotalPriceExcludingVat();
        return totalPriceExcludingVat
                .add(totalPriceExcludingVat.multiply(VAT_RATE))
                .setScale(2, RoundingMode.HALF_UP);
    }
}
