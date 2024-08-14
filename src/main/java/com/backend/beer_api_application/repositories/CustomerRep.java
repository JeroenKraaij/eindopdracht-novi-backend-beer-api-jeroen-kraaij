package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.models.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerRep {

    List<Customer> selectAllCustomers();
    Optional<Customer> selectCustomerById(Integer id);
    void insertCustomer(Customer customer);
    boolean existsPersonWithEmail(String email);
    boolean existsPersonWithId(Integer id);
    void deleteCustomerById(Integer customerId);
    void updateCustomer(Customer update);
}
