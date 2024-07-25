package com.backend.beer_api_application.dtos;

import com.backend.beer_api_application.models.Customer;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerDao {

    List<Customer> selectAllCustomers();
    Optional<Customer>selectCustomerById(Long id);

    Optional<Customer> selectCustomerById(Integer id);
}
