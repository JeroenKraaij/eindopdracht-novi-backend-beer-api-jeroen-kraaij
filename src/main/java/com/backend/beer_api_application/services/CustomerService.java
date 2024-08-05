package com.backend.beer_api_application.services;

import com.backend.beer_api_application.controllers.CustomerRegistrationRequest;
import com.backend.beer_api_application.dtos.CustomerDao;
import com.backend.beer_api_application.exceptions.DuplicateResourceException;
import com.backend.beer_api_application.exceptions.ResourceNotFoundException;
import com.backend.beer_api_application.models.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerDao customerDao;

    public CustomerService(@Qualifier("jpa" ) CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers() {
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerDao.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(
            CustomerRegistrationRequest customerRegistrationRequest) {

        // check if email exists
            String email = customerRegistrationRequest.email();
            if (customerDao.existsPersonWithEmail(email)) {
                throw new DuplicateResourceException(
                        "email already taken"
                );
            }


        // add Customer
        Customer customer = new Customer(
                customerRegistrationRequest.name(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.age()
        );

        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer customerId) {
        if (!customerDao.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException(
                    "customer with id [%s] not found".formatted(customerId)
            );
        }

        customerDao.deleteCustomerById(customerId);
    }


}






