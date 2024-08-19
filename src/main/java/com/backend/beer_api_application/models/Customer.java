package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

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

    @Column(unique = true)
    @Setter
    private String email;

    @Column
    @Setter
    private String phone;

    @Column
    @Setter
    private String dateOfBirth;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders;

    // Default constructor
    public Customer() {}

    // All-args constructor
//    public Customer(String firstname, String surname, String address, String houseNumber, String zipcode, String city, String email, String phone, String dateOfBirth) {
//        this.firstname = firstname;
//        this.surname = surname;
//        this.address = address;
//        this.houseNumber = houseNumber;
//        this.zipcode = zipcode;
//        this.city = city;
//        this.email = email;
//        this.phone = phone;
//        this.dateOfBirth = String.valueOf(dateOfBirth);
//    }

//    // Getters and Setters
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getFirstname() {
//        return firstname;
//    }
//
//    public void setFirstname(String firstname) {
//        this.firstname = firstname;
//    }
//
//    public String getSurname() {
//        return surname;
//    }
//
//    public void setSurname(String surname) {
//        this.surname = surname;
//    }
//
//    public String getAddress() {
//        return address;
//    }
//
//    public void setAddress(String address) {
//        this.address = address;
//    }
//
//    public String getHouseNumber() {
//        return houseNumber;
//    }
//
//    public void setHouseNumber(String houseNumber) {
//        this.houseNumber = houseNumber;
//    }
//
//    public String getZipcode() {
//        return zipcode;
//    }
//
//    public void setZipcode(String zipcode) {
//        this.zipcode = zipcode;
//    }
//
//    public String getCity() {
//        return city;
//    }
//
//    public void setCity(String city) {
//        this.city = city;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
//
//    public String getPhone() {
//        return phone;
//    }
//
//    public void setPhone(String phone) {
//        this.phone = phone;
//    }
//
//    public String getDateOfBirth() {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(String dateOfBirth) {
//        this.dateOfBirth = String.valueOf(dateOfBirth);
//    }
//
//    // Equals & HashCode
//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Customer customer = (Customer) o;
//        return Objects.equals(id, customer.id) &&
//                Objects.equals(firstname, customer.firstname) &&
//                Objects.equals(surname, customer.surname) &&
//                Objects.equals(address, customer.address) &&
//                Objects.equals(houseNumber, customer.houseNumber) &&
//                Objects.equals(zipcode, customer.zipcode) &&
//                Objects.equals(city, customer.city) &&
//                Objects.equals(email, customer.email) &&
//                Objects.equals(phone, customer.phone) &&
//                Objects.equals(dateOfBirth, customer.dateOfBirth);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(id, firstname, surname, address, houseNumber, zipcode, city, email, phone, dateOfBirth);
//    }
//
//    // toString
//    @Override
//    public String toString() {
//        return "Customer{" +
//                "id=" + id +
//                ", firstname='" + firstname + '\'' +
//                ", surname='" + surname + '\'' +
//                ", address='" + address + '\'' +
//                ", houseNumber='" + houseNumber + '\'' +
//                ", zipcode='" + zipcode + '\'' +
//                ", city='" + city + '\'' +
//                ", email='" + email + '\'' +
//                ", phone='" + phone + '\'' +
//                ", dateOfBirth=" + dateOfBirth +
//                '}';
//    }


}
