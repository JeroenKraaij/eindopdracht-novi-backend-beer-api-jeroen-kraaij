package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.CustomerInputDto;
import com.backend.beer_api_application.dto.output.CustomerOutputDto;
import com.backend.beer_api_application.dto.mapper.CustomerMapper;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.repositories.CustomerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerOutputDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::transferToCustomerOutputDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerOutputDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Customer not found with ID: " + id));
        return CustomerMapper.transferToCustomerOutputDto(customer);
    }

    @Transactional
    public CustomerOutputDto addCustomer(CustomerInputDto customerInputDto) {
        Customer customer = CustomerMapper.transferToCustomerEntity(customerInputDto);
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.transferToCustomerOutputDto(savedCustomer);
    }

    @Transactional
    public CustomerOutputDto updateCustomer(Long id, CustomerInputDto customerInputDto) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new RecordNotFoundException("Customer not found with ID: " + id));

        updateCustomerEntity(customer, customerInputDto);
        Customer updatedCustomer = customerRepository.save(customer);

        return CustomerMapper.transferToCustomerOutputDto(updatedCustomer);
    }

    @Transactional
    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new RecordNotFoundException("Customer not found with ID: " + id);
        }
        customerRepository.deleteById(id);
    }

    private void updateCustomerEntity(Customer customer, CustomerInputDto customerInputDto) {
        customer.setFirstname(customerInputDto.getFirstname());
        customer.setSurname(customerInputDto.getSurname());
        customer.setAddress(customerInputDto.getAddress());
        customer.setHouseNumber(customerInputDto.getHouseNumber());
        customer.setZipcode(customerInputDto.getZipcode());
        customer.setCity(customerInputDto.getCity());
        customer.setEmail(customerInputDto.getEmail());
        customer.setPhone(customerInputDto.getPhone());
        customer.setDateOfBirth(customerInputDto.getDateOfBirth());
    }
}
