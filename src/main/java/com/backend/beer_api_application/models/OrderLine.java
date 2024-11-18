package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Data
@Table(name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Beer beer;

    @ManyToOne
    @JoinColumn()
    private Order order;

    @Column(nullable = false)
    private Integer quantity;

    @Getter
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtPurchase;

    private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.21);

    public OrderLine(Beer beer, Integer quantity) {
        if (beer.getInStock() < quantity) {
            throw new IllegalArgumentException("Out of Stock: Only " + beer.getInStock() + " beers available");
        }
        this.beer = beer;
        this.quantity = quantity;

        setPriceAtPurchase(priceAtPurchase != null ? priceAtPurchase : beer.getPrice());

        beer.decrementStock(quantity);
    }

    public OrderLine() {}

    public BigDecimal getTotalPriceExcludingVat() {
        return priceAtPurchase.multiply(BigDecimal.valueOf(quantity))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal getTotalPriceIncludingVat() {
        BigDecimal totalPriceExcludingVat = getTotalPriceExcludingVat();
        return totalPriceExcludingVat
                .add(totalPriceExcludingVat.multiply(VAT_RATE))
                .setScale(2, RoundingMode.HALF_UP);
    }

    public void setPriceAtPurchase(BigDecimal priceAtPurchase) {
        if (priceAtPurchase == null) {
            throw new IllegalArgumentException("priceAtPurchase cannot be null");
        }
        this.priceAtPurchase = priceAtPurchase;
    }
}
