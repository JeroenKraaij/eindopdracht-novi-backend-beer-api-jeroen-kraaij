package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dtos.CustomerRegistrationRequest;
import com.backend.beer_api_application.dtos.CustomerUpdateRequest;
import com.backend.beer_api_application.repositories.CustomerRep;
import com.backend.beer_api_application.exceptions.DuplicateResourceException;
import com.backend.beer_api_application.exceptions.ResourceNotFoundException;
import com.backend.beer_api_application.models.Customer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {

    private final CustomerRep customerRep;

    public CustomerService(@Qualifier("jpa") CustomerRep customerRep) {
        this.customerRep = customerRep;
    }

    public List<Customer> getAllCustomers() {
        return customerRep.selectAllCustomers();
    }

    public Customer getCustomer(Integer id) {
        return customerRep.selectCustomerById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(id)
                ));
    }

    public void addCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        String email = customerRegistrationRequest.email();
        if (customerRep.existsPersonWithEmail(email)) {
            throw new DuplicateResourceException("Email already taken");
        }

        Customer customer = new Customer(
                customerRegistrationRequest.firstname(),
                customerRegistrationRequest.surname(),
                customerRegistrationRequest.address(),
                customerRegistrationRequest.houseNumber(),
                customerRegistrationRequest.zipcode(),
                customerRegistrationRequest.city(),
                customerRegistrationRequest.email(),
                customerRegistrationRequest.phone(),
                customerRegistrationRequest.dateOfBirth()
        );

        customerRep.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer customerId) {
        if (!customerRep.existsPersonWithId(customerId)) {
            throw new ResourceNotFoundException("Customer with id [%s] not found".formatted(customerId));
        }

        customerRep.deleteCustomerById(customerId);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest) {
        Customer existingCustomer = customerRep.selectCustomerById(customerId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Customer with id [%s] not found".formatted(customerId)
                ));

        if (updateRequest.firstname() != null && !updateRequest.firstname().isEmpty()) {
            existingCustomer.setFirstname(updateRequest.firstname());
        }
        if (updateRequest.surname() != null && !updateRequest.surname().isEmpty()) {
            existingCustomer.setSurname(updateRequest.surname());
        }
        if (updateRequest.address() != null && !updateRequest.address().isEmpty()) {
            existingCustomer.setAddress(updateRequest.address());
        }
        if (updateRequest.houseNumber() != null && !updateRequest.houseNumber().isEmpty()) {
            existingCustomer.setHouseNumber(updateRequest.houseNumber());
        }
        if (updateRequest.zipcode() != null && !updateRequest.zipcode().isEmpty()) {
            existingCustomer.setZipcode(updateRequest.zipcode());
        }
        if (updateRequest.city() != null && !updateRequest.city().isEmpty()) {
            existingCustomer.setCity(updateRequest.city());
        }
        if (updateRequest.email() != null && !updateRequest.email().isEmpty()) {
            if (customerRep.existsPersonWithEmail(updateRequest.email())) {
                throw new DuplicateResourceException("Email already taken");
            }
            existingCustomer.setEmail(updateRequest.email());
        }
        if (updateRequest.phone() != null && !updateRequest.phone().isEmpty()) {
            existingCustomer.setPhone(updateRequest.phone());
        }
        if (updateRequest.dateOfBirth() != null) {
            existingCustomer.setDateOfBirth(String.valueOf(updateRequest.dateOfBirth()));
        }

        customerRep.updateCustomer(existingCustomer);
    }
}
