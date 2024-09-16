package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.CustomerInputDto;
import com.backend.beer_api_application.dto.output.CustomerOutputDto;
import com.backend.beer_api_application.models.Customer;
import org.springframework.stereotype.Service;

@Service
public class CustomerMapper {


    public static CustomerOutputDto transferToCustomerOutputDto(Customer customer) {
        CustomerOutputDto customerOutputDto = new CustomerOutputDto();
        customerOutputDto.setId(customer.getId());
        customerOutputDto.setFirstname(customer.getFirstname());
        customerOutputDto.setSurname(customer.getSurname());
        customerOutputDto.setAddress(customer.getAddress());
        customerOutputDto.setZipcode(customer.getZipcode());
        customerOutputDto.setCity(customer.getCity());
        if (customer.getUser() != null) {
            customerOutputDto.setEmail(customer.getUser().getEmail());
        }
        customerOutputDto.setHouseNumber(customer.getHouseNumber());
        customerOutputDto.setPhone(customer.getPhone());
        customerOutputDto.setDateOfBirth(customer.getDateOfBirth());
        return customerOutputDto;
    }

    public static Customer transferToCustomerEntity(CustomerInputDto customerInputDto) {
        Customer customer = new Customer();
        customer.setFirstname(customerInputDto.getFirstname());
        customer.setSurname(customerInputDto.getSurname());
        customer.setAddress(customerInputDto.getAddress());
        customer.setHouseNumber(customerInputDto.getHouseNumber());
        customer.setZipcode(customerInputDto.getZipcode());
        customer.setCity(customerInputDto.getCity());
        customer.setPhone(customerInputDto.getPhone());
        customer.setDateOfBirth(customerInputDto.getDateOfBirth().toString());
        return customer;
    }
}
