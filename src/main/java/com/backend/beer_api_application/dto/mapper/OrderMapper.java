package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.OrderInputDto;
import com.backend.beer_api_application.dto.output.OrderOutputDto;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.Customer;

import java.util.stream.Collectors;

public class OrderMapper {

    // Map Order entity to OrderOutputDto
    public static OrderOutputDto transferToOrderOutputDto(Order order) {
        OrderOutputDto orderOutputDto = new OrderOutputDto();
        orderOutputDto.setId(order.getId());
        orderOutputDto.setCustomerName(order.getCustomer().getFirstname() + " " + order.getCustomer().getSurname());

        // Populate OrderLines in the OutputDto using the OrderLineMapper
        orderOutputDto.setOrderLines(order.getOrderLines().stream()
                .map(OrderLineMapper::transferToOrderLineOutputDto)
                .collect(Collectors.toList()));

        return orderOutputDto;
    }

    // Map OrderInputDto to Order entity
    public static Order transferToOrderEntity(OrderInputDto dto, Customer customer) {
        Order order = new Order();
        order.setCustomer(customer);

        // Populate OrderLines from DTOs (handled by OrderLineMapper)
        dto.getOrderLines().forEach(orderLineInputDto -> {
            order.addOrderLine(OrderLineMapper.transferToOrderLineEntity(orderLineInputDto));
        });

        return order;
    }
}
