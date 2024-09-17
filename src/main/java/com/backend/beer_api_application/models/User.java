package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Table(name = "users")
public class User {

    @Id
    @Column(nullable = false, unique = true)
    @Setter
    private String username;

    @Column(nullable = false, length = 255)
    @Setter
    private String password;

    @Column
    @Setter
    private String email;

    @Getter
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    @Column(nullable = false)
    @Setter
    private boolean enabled = true;

    @Column
    @Setter
    private String apikey;



    // One-to-One relationship with Customer
    @OneToOne(mappedBy = "user")
    @Setter
    private Customer customer;
    
    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}
