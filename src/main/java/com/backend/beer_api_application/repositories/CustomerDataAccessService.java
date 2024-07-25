package com.backend.beer_api_application.repositories;

import com.backend.beer_api_application.dtos.CustomerDao;
import com.backend.beer_api_application.models.Customer;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CustomerDataAccessService implements CustomerDao {

    // db
    private static final List<Customer> customers;

    static {
        customers = new ArrayList<>();

        Customer alex = new Customer(
                1,
                "Alex",
                "alex@gmail.com",
                21,
                "06-1234567"
        );
        customers.add(alex);

        Customer jamila = new Customer(
                2,
                "Jamila",
                "jamila@gmail.com",
                22,
                "06-1234589"
        );
        customers.add(jamila);

        Customer bob = new Customer(
                3,
                "Bob",
                "bob@gmail.com",
                25,
                "06-98765432"
        );
        customers.add(bob);
    }

    @Override
    public List<Customer> selectAllCustomers() {
        return customers;
    }

    @Override
    public Optional<Customer> selectCustomerById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Customer> selectCustomerById(Integer id) {
        return customers.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
    }
}