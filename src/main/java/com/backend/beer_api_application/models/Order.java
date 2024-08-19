package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
@Table(name = "Orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinColumn(name = "beer_id", nullable = false)
    private List<Beer> beer;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(nullable = false)
    private Integer quantity;

    public Order(Beer beer, Customer customer, Integer quantity) {}


//    public Order(List<Beer> beer, Customer customer, Integer quantity) {
//       this.beer = beer;
//        this.customer = customer;
//        this.quantity = quantity;
//    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
//
//    public Integer getQuantity() {
//        return quantity;
//    }
//
//    public void setQuantity(Integer quantity) {
//        this.quantity = quantity;
//    }
//
//    public BigDecimal getBeerPrice() {
//        return beerPrice;
//    }
//
//    public void setBeerPrice(BigDecimal beerPrice) {
//        this.beerPrice = beerPrice;
//    }
//
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Order order = (Order) o;
//        return Objects.equals(id, order.id) &&
//                Objects.equals(beer, order.beer) &&
//                Objects.equals(customer, order.customer) &&
//                Objects.equals(quantity, order.quantity) &&
//                Objects.equals(beerPrice, order.beerPrice);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, beer, customer, quantity, beerPrice);
//    }
//
//    @Override
//    public String toString() {
//        return "Order{" +
//                "id=" + id +
//                ", beer=" + beer +
//                ", customer=" + customer +
//                ", quantity=" + quantity +
//                ", beerPrice=" + beerPrice +
//                '}';
//    }
}
