package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.models.Beer;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    // Convert OrderLine entity to OrderLineOutputDto
    public OrderLineOutputDto toOrderLineOutputDto(OrderLine orderLine) {
        OrderLineOutputDto dto = new OrderLineOutputDto();
        dto.setId(orderLine.getId());
        dto.setBeerName(orderLine.getBeer().getName());
        dto.setAmount(orderLine.getQuantity());
        dto.setPriceAtPurchase(orderLine.getPriceAtPurchase());
        dto.setTotalPriceExcludingVat(orderLine.getTotalPriceExcludingVat());
        dto.setTotalPriceIncludingVat(orderLine.getTotalPriceIncludingVat());

        return dto;
    }

    // Convert OrderLineInputDto to OrderLine entity
    public static OrderLine toOrderLineEntity(OrderLineInputDto dto, Beer beer) {
        OrderLine orderLine = new OrderLine();
        orderLine.setBeer(beer);
        orderLine.setQuantity(dto.getQuantity());
        orderLine.setPriceAtPurchase(orderLine.getTotalPriceIncludingVat());

        return orderLine;
    }
}
