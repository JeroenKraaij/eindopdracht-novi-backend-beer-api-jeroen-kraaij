package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.output.OrderOutputDto;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.Customer;

import java.util.stream.Collectors;

public class OrderMapper {

    public static OrderOutputDto transferToOrderOutputDto(Order order) {
        OrderOutputDto orderOutputDto = new OrderOutputDto();
        orderOutputDto.setId(order.getId());

        // Extracting customer details from the associated Customer entity
        Customer customer = order.getCustomer();
        orderOutputDto.setCustomerName(customer.getFirstname() + " " + customer.getSurname());
        orderOutputDto.setCustomerAddress(customer.getAddress());
        orderOutputDto.setCustomerHouseNumber(customer.getHouseNumber());
        orderOutputDto.setCustomerZipcode(customer.getZipcode());
        orderOutputDto.setCustomerCity(customer.getCity());
        orderOutputDto.setCustomerEmail(customer.getEmail());
        orderOutputDto.setOrderLines(order.getOrderLines().stream()
                .map(OrderLineMapper::transferToOrderLineOutputDto)
                .collect(Collectors.toList()));


        return orderOutputDto;
    }
}
