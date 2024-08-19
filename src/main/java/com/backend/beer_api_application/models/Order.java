package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter

@Table(name = "Orders")
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
    @Setter
    private List<OrderLine> orderLines = new ArrayList<>();

    // Helper methods
    public void addOrderLine(OrderLine orderLine) {
        orderLines.add(orderLine);
        orderLine.setOrder(this);
    }

    public void removeOrderLine(OrderLine orderLine) {
        orderLines.remove(orderLine);
        orderLine.setOrder(null);
    }

    // Default constructor
    public Order() {}
}
