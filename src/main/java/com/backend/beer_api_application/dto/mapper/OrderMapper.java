package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.output.OrderOutputDto;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.Customer;
import com.backend.beer_api_application.models.Order;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.repositories.BeerRepository;
import com.backend.beer_api_application.utils.OrderCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderMapper {

    private final BeerRepository beerRepository;
    private final OrderLineMapper orderLineMapper;

    @Autowired
    public OrderMapper(BeerRepository beerRepository, OrderLineMapper orderLineMapper) {
        this.beerRepository = beerRepository;
        this.orderLineMapper = orderLineMapper;
    }

    public OrderOutputDto TransferToOrderOutputDto(Order order) {
        OrderOutputDto dto = new OrderOutputDto();
        dto.setId(order.getId());
        setCustomerDetails(order, dto);
        setOrderDetails(order, dto);
        return dto;
    }

    private void setCustomerDetails(Order order, OrderOutputDto dto) {
        Customer customer = order.getCustomer();
        dto.setCustomerId(customer.getId());
        dto.setCustomerName(customer.getFirstname() + " " + customer.getSurname());
        dto.setCustomerAddress(customer.getAddress());
        dto.setCustomerHouseNumber(customer.getHouseNumber());
        dto.setCustomerZipcode(customer.getZipcode());
        dto.setCustomerCity(customer.getCity());
        dto.setCustomerEmail(customer.getUser().getEmail());
    }

    private void setOrderDetails(Order order, OrderOutputDto dto) {
        dto.setOrderDate(order.getOrderDate());
        dto.setOrderStatus(order.getOrderStatus().name());
        dto.setTotalAmountExcludingVat(order.getTotalAmountExcludingVat());
        dto.setTotalAmountIncludingVat(order.getTotalAmountIncludingVat());
        dto.setVatAmount(OrderCalculator.getVatAmount(order));
        dto.setOrderLines(order.getOrderLines().stream()
                .map(orderLineMapper::transferToOrderLineOutputDto)
                .collect(Collectors.toList()));
        dto.setDeliveryAddress(order.getDeliveryAddress());
        dto.setPaymentMethod(String.valueOf(order.getPaymentMethod()));
    }

    public List<OrderLine> transferToOrderLineList(List<OrderLineInputDto> dtos) {
        return dtos.stream()
                .map(this::transferToOrderLine)
                .collect(Collectors.toList());
    }

    public OrderLine transferToOrderLine(OrderLineInputDto dto) {
        Beer beer = beerRepository.findById(dto.getBeerId())
                .orElseThrow(() -> new RuntimeException("Beer with ID " + dto.getBeerId() + " not found"));

        return OrderLineMapper.transferToOrderLineEntity(dto, beer);  // Call the correct method here
    }
}
