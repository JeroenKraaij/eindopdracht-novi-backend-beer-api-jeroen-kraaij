package com.backend.beer_api_application.services;

import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.backend.beer_api_application.dto.input.CustomerInputDto;
import com.backend.beer_api_application.dto.output.CustomerOutputDto;
import com.backend.beer_api_application.dto.mapper.CustomerMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<CustomerOutputDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::transferToOutputDto)
                .collect(Collectors.toList());
    }

    public CustomerOutputDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerMapper::transferToOutputDto)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
    }

    public CustomerOutputDto createCustomer(CustomerInputDto customerInputDto) {
        Customer customer = CustomerMapper.transferToCustomerEntity(customerInputDto);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.transferToOutputDto(savedCustomer);
    }

    public CustomerOutputDto updateCustomer(Long id, CustomerInputDto customerInputDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));

        customer.setFirstname(customerInputDto.getFirstname());
        customer.setSurname(customerInputDto.getSurname());
        customer.setAddress(customerInputDto.getAddress());
        customer.setHouseNumber(customerInputDto.getHouseNumber());
        customer.setZipcode(customerInputDto.getZipcode());
        customer.setCity(customerInputDto.getCity());
        customer.setEmail(customerInputDto.getEmail());
        customer.setPhone(customerInputDto.getPhone());
        customer.setDateOfBirth(customerInputDto.getDateOfBirth());

        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerMapper.transferToOutputDto(updatedCustomer);
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
