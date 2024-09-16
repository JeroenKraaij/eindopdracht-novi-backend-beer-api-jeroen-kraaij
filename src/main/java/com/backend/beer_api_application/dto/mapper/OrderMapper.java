package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.output.OrderOutputDto;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.BeerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    private final BeerRepository beerRepository;

    public OrderMapper(BeerRepository beerRepository) {
        this.beerRepository = beerRepository;
    }

    // Maps Order entity to OrderOutputDto
    public OrderOutputDto transferToOrderOutputDto(Order order) {
        OrderOutputDto orderOutputDto = new OrderOutputDto();
        orderOutputDto.setId(order.getId());

        // Extracting customer details
        Customer customer = order.getCustomer();
        orderOutputDto.setCustomerName(customer.getFirstname() + " " + customer.getSurname());
        orderOutputDto.setCustomerAddress(customer.getAddress());
        orderOutputDto.setCustomerHouseNumber(customer.getHouseNumber());
        orderOutputDto.setCustomerZipcode(customer.getZipcode());
        orderOutputDto.setCustomerCity(customer.getCity());
        orderOutputDto.setCustomerEmail(customer.getUser().getEmail());

        // Setting order-related fields
        orderOutputDto.setOrderDate(order.getOrderDate());
        orderOutputDto.setOrderStatus(order.getOrderStatus().name());  // Convert enum to string

        // Calculating total amounts (already calculated in the entity)
        orderOutputDto.setTotalAmountExcludingVat(order.getTotalAmountExcludingVat());
        orderOutputDto.setTotalAmountIncludingVat(order.getTotalAmountIncludingVat());

        // Setting list of OrderLines
        orderOutputDto.setOrderLines(order.getOrderLines().stream()
                .map(OrderLineMapper::transferToOrderLineOutputDto)
                .collect(Collectors.toList()));

        // Set additional optional fields
        orderOutputDto.setDeliveryAddress(order.getDeliveryAddress());
        orderOutputDto.setPaymentMethod(order.getPaymentMethod());

        return orderOutputDto;
    }

    // Maps List of OrderLineInputDto to List of OrderLine entities
    public List<OrderLine> toOrderLineList(List<OrderLineInputDto> orderLineInputDtos) {
        return orderLineInputDtos.stream()
                .map(this::toOrderLine)
                .collect(Collectors.toList());
    }

    // Maps OrderLineInputDto to OrderLine entity
    public OrderLine toOrderLine(OrderLineInputDto orderLineInputDto) {
        OrderLine orderLine = new OrderLine();

        // Fetching Beer entity from the repository using beerId from the DTO
        Beer beer = beerRepository.findById(orderLineInputDto.getBeerId())
                .orElseThrow(() -> new RuntimeException("Beer with ID " + orderLineInputDto.getBeerId() + " not found"));

        // Set the beer, amount, and price
        orderLine.setBeer(beer);
        orderLine.setAmount(orderLineInputDto.getQuantity());
        orderLine.setPriceAtPurchase(orderLine.getTotalPriceIncludingVat());

        return orderLine;
    }
}
