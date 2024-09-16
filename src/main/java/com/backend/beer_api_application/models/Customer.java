package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "Customers")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Setter
    private String firstname;

    @Column
    @Setter
    private String surname;

    @Column
    @Setter
    private String address;

    @Column
    @Setter
    private String houseNumber;

    @Column
    @Setter
    private String zipcode;

    @Column
    @Setter
    private String city;

    @Column
    @Setter
    private String phone;

    @Column
    @Setter
    private String dateOfBirth;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    @Setter
    private List<Order> orders = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "user_customer", referencedColumnName = "username")
    @Setter
    private User user;

    public Customer() {
    }

    public void addOrder(Order order) {
        orders.add(order);
        order.setCustomer(this);
    }

    public void removeOrder(Order order) {
        orders.remove(order);
        order.setCustomer(null);
    }
}
