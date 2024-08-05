
package com.backend.beer_api_application.models;

import jakarta.persistence.*;
import java.util.Objects;

    @Entity
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
        private Integer id;

        @Column(
            nullable = false
        )
        private String name;

        @Column(
                nullable = false
        )
        private String email;


        private Integer age;

        @Column(
                nullable = false
        )
        private String phone;

        public Customer() {}

        public Customer(Integer id, String name, String email, Integer age, String phone) {
            this.id = id;
            this.name = name;
            this.email = email;
            this.age = age;
            this.phone = phone;
        }

        public Customer( String name, String email, Integer age, String phone) {
            this.name = name;
            this.email = email;
            this.age = age;
            this.phone = phone;
        }

        public Customer(String name, String email, Integer age) {
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public String getPhone() {
            return phone;
        }
        public void setPhone(String phone) {
            this.phone = phone;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Customer customer = (Customer) o;
            return Objects.equals(id, customer.id) && Objects.equals(name, customer.name) && Objects.equals(email, customer.email) && Objects.equals(age, customer.age) && Objects.equals(phone, customer.phone);
        }

        @Override
        public int hashCode() {
            return Objects.hash (id, name, email, age, phone);
        }

        @Override
        public String toString() {
            return "Customer{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", email='" + email + '\'' +
                    ", age=" + age +
                    ", phone='" + phone + '\'' +
                    '}';
        }
    }

