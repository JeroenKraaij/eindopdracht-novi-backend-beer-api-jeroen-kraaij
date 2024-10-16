package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Entity
@Data
@Table (name = "order_line")
public class OrderLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Many-to-one relationship with Beer
    @ManyToOne
    @JoinColumn(nullable = false)
    private Beer beer;

    // Many-to-one relationship with Order
    @ManyToOne
    @JoinColumn(nullable = false)
    private Order order;

    // Quantity of beers in the order
    @Column(nullable = false)
    private Integer quantity;

    // Price at the time of purchase
    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal priceAtPurchase;

    // VAT constant (21%)
    private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.21);

    // Constructor with stock validation
    public OrderLine(Beer beer, Integer quantity) {
        if (beer.getInStock() < quantity) {
            throw new IllegalArgumentException("Out of Stock: Only " + beer.getInStock() + " beers available");
        }
        this.beer = beer;
        this.quantity = quantity;
        this.priceAtPurchase = beer.getPrice();

        // Adjust the beer's stock (reduce inStock after order)
        beer.decrementStock(quantity);
    }

    // Default constructor
    public OrderLine() {}

    // Calculate total price excluding VAT
    public BigDecimal getTotalPriceExcludingVat() {
        return priceAtPurchase.multiply(BigDecimal.valueOf(quantity))
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
