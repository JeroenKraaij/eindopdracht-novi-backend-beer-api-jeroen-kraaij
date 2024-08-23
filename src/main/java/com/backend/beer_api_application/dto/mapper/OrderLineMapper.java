package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.models.Beer;

public class OrderLineMapper {

    // Converts an OrderLine entity to an OrderLineOutputDto
    public static OrderLineOutputDto transferToOrderLineOutputDto(OrderLine orderLine) {
        OrderLineOutputDto orderLineOutputDto = new OrderLineOutputDto();
        orderLineOutputDto.setId(orderLine.getId());
        orderLineOutputDto.setBeerName(orderLine.getBeer().getName());
        orderLineOutputDto.setAmount(orderLine.getAmount());
        orderLineOutputDto.setPriceAtPurchase(orderLine.getPriceAtPurchase());
        orderLineOutputDto.setTotalPriceExcludingVat(orderLine.getTotalPriceExcludingVat());
        orderLineOutputDto.setTotalPriceIncludingVat(orderLine.getTotalPriceIncludingVat());
        return orderLineOutputDto;
    }

    // Converts an OrderLineInputDto to an OrderLine entity, setting the Beer entity
    public static OrderLine transferToOrderLineEntity(OrderLineInputDto dto, Beer beer) {
        OrderLine orderLine = new OrderLine();
        orderLine.setBeer(beer);  // Beer is already resolved from the BeerRepository or service
        orderLine.setAmount(dto.getAmount());
        orderLine.setPriceAtPurchase(dto.getPriceAtPurchase());
        return orderLine;
    }
}
