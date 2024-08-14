package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Objects;

@Entity
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "beer_id", nullable = false)
    private Beer beer;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Integer quantity;

    @Column(nullable = false)
    private BigDecimal beerPrice;

    public Order() {}

    public Order(Beer beer, Customer customer, Integer quantity) {
        this.beer = beer;
        this.customer = customer;
        this.quantity = quantity;
        this.beerPrice = beer.getPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Beer getBeer() {
        return beer;
    }

    public void setBeer(Beer beer) {
        this.beer = beer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getBeerPrice() {
        return beerPrice;
    }

    public void setBeerPrice(BigDecimal beerPrice) {
        this.beerPrice = beerPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(beer, order.beer) &&
                Objects.equals(customer, order.customer) &&
                Objects.equals(quantity, order.quantity) &&
                Objects.equals(beerPrice, order.beerPrice);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, beer, customer, quantity, beerPrice);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", beer=" + beer.getName() +
                ", customer=" + customer.getFirstname() + " " + customer.getSurname() +
                ", quantity=" + quantity +
                ", beerPrice=" + beerPrice +
                '}';
    }
}
