package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.CustomerInputDto;
import com.backend.beer_api_application.dto.output.CustomerOutputDto;
import com.backend.beer_api_application.models.Customer;

public class CustomerMapper {



    public static CustomerOutputDto transferToOutputDto(Customer customer) {
        CustomerOutputDto customerOutputDto = new CustomerOutputDto();
        customerOutputDto.setId(customer.getId());
        customerOutputDto.setFirstname(customer.getFirstname());
        customerOutputDto.setSurname(customer.getSurname());
        customerOutputDto.setAddress(customer.getAddress());
        customerOutputDto.setZipcode(customer.getZipcode());
        customerOutputDto.setCity(customer.getCity());
        customerOutputDto.setEmail(customer.getEmail());
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
        customer.setEmail(customerInputDto.getEmail());
        customer.setPhone(customerInputDto.getPhone());
        customer.setDateOfBirth(customerInputDto.getDateOfBirth());
        return customer;
    }
}
