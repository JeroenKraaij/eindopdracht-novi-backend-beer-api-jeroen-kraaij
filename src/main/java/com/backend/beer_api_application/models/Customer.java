package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "Customers")
public class Customer {

    @Id
    @SequenceGenerator(
            name = "customer_id_sequence",
            sequenceName = "customer_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "customer_id_sequence"
    )
    @Column
    private Long id;

    @Column
    private String firstname;

    @Column
    private String surname;

    @Column
    private String address;

    @Column
    private String houseNumber;

    @Column
    private String zipcode;

    @Column
    private String city;

    @Column(unique = true)
    private String email;

    @Column
    private String phone;

    @Column
    private String dateOfBirth;

    public Customer() {}

    public Customer(Long id, String firstname, String surname, String address, String houseNumber, String zipcode, String city, String email, String phone, String dateOfBirth) {
        this.id = id;
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.houseNumber = houseNumber;
        this.zipcode = zipcode;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = String.valueOf(dateOfBirth);
    }

    public Customer(String firstname, String surname, String address, String houseNumber, String zipcode, String city, String email, String phone, String dateOfBirth) {
        this.firstname = firstname;
        this.surname = surname;
        this.address = address;
        this.houseNumber = houseNumber;
        this.zipcode = zipcode;
        this.city = city;
        this.email = email;
        this.phone = phone;
        this.dateOfBirth = String.valueOf(dateOfBirth);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = String.valueOf(dateOfBirth);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return Objects.equals(id, customer.id) &&
                Objects.equals(firstname, customer.firstname) &&
                Objects.equals(surname, customer.surname) &&
                Objects.equals(address, customer.address) &&
                Objects.equals(houseNumber, customer.houseNumber) &&
                Objects.equals(zipcode, customer.zipcode) &&
                Objects.equals(city, customer.city) &&
                Objects.equals(email, customer.email) &&
                Objects.equals(phone, customer.phone) &&
                Objects.equals(dateOfBirth, customer.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstname, surname, address, houseNumber, zipcode, city, email, phone, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", surname='" + surname + '\'' +
                ", address='" + address + '\'' +
                ", houseNumber='" + houseNumber + '\'' +
                ", zipcode='" + zipcode + '\'' +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }


}
