package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.services.BeerService;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;

public class OrderLineMapper {

    private final BeerService beerService;

    public OrderLineMapper(BeerService beerService) {
        this.beerService = beerService;
    }

    // Map OrderLine entity to OrderLineOutputDto
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

    // Map OrderLineInputDto to OrderLine entity
    public static OrderLine transferToOrderLineEntity(OrderLineInputDto dto) {
        Beer beer = beerService.getBeerById(dto.getBeerId())
                .orElseThrow(() -> new RecordNotFoundException("Beer not found with ID: " + dto.getBeerId()));

        OrderLine orderLine = new OrderLine();
        orderLine.setBeer(beer);  // Set the Beer entity in the OrderLine
        orderLine.setAmount(dto.getAmount());
        orderLine.setPriceAtPurchase(dto.getPriceAtPurchase());
        return orderLine;
    }
}

