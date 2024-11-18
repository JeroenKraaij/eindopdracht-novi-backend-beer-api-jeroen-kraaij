package com.backend.beer_api_application.services;

import com.backend.beer_api_application.dto.input.CustomerInputDto;
import com.backend.beer_api_application.dto.output.CustomerOutputDto;
import com.backend.beer_api_application.dto.mapper.CustomerMapper;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.User;
import com.backend.beer_api_application.repositories.CustomerRepository;
import com.backend.beer_api_application.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final UserRepository userRepository;

    public CustomerService(CustomerRepository customerRepository, UserRepository userRepository) {
        this.customerRepository = customerRepository;
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    public List<CustomerOutputDto> getAllCustomers() {
        return customerRepository.findAll().stream()
                .map(CustomerMapper::transferToCustomerOutputDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public CustomerOutputDto getCustomerById(Long id) {
        return customerRepository.findById(id)
                .map(CustomerMapper::transferToCustomerOutputDto)
                .orElse(null);
    }

    @Transactional(readOnly = true)
    public boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    public boolean userHasCustomer(String username) {
        return userRepository.findUserByUsername(username)
                .map(user -> user.getCustomer() != null)
                .orElse(false);
    }

    @Transactional
    public CustomerOutputDto addCustomer(CustomerInputDto customerInputDto, Authentication userDetails) {
        Optional<User> user = userRepository.findUserByUsername(userDetails.getName());
        if (user.isEmpty() || user.get().getCustomer() != null) {
            return null;
        }

        Customer customer = CustomerMapper.transferToCustomerEntity(customerInputDto);
        customer.setUser(user.get());
        Customer savedCustomer = customerRepository.save(customer);
        return CustomerMapper.transferToCustomerOutputDto(savedCustomer);
    }

    @Transactional
    public CustomerOutputDto updateCustomer(Long id, CustomerInputDto customerInputDto) {
        Optional<Customer> customerOptional = customerRepository.findById(id);
        if (customerOptional.isEmpty()) {
            return null;
        }

        Customer customer = customerOptional.get();
        updateCustomerEntity(customer, customerInputDto);
        Customer updatedCustomer = customerRepository.save(customer);
        return CustomerMapper.transferToCustomerOutputDto(updatedCustomer);
    }

    private void updateCustomerEntity(Customer customer, CustomerInputDto customerInputDto) {
        customer.setFirstname(customerInputDto.getFirstname());
        customer.setSurname(customerInputDto.getSurname());
        customer.setAddress(customerInputDto.getAddress());
        customer.setHouseNumber(customerInputDto.getHouseNumber());
        customer.setZipcode(customerInputDto.getZipcode());
        customer.setCity(customerInputDto.getCity());
        customer.setPhone(customerInputDto.getPhone());
        customer.setDateOfBirth(customerInputDto.getDateOfBirth().toString());
    }

    @Transactional
    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
