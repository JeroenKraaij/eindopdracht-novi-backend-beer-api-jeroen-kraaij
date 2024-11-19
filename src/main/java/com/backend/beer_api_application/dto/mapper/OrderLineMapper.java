package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.input.OrderLineInputDto;
import com.backend.beer_api_application.dto.output.OrderLineOutputDto;
import com.backend.beer_api_application.models.OrderLine;
import com.backend.beer_api_application.models.Beer;
import org.springframework.stereotype.Service;

@Service
public class OrderLineMapper {

    public OrderLineOutputDto transferToOrderLineOutputDto(OrderLine orderLine) {
        OrderLineOutputDto dto = new OrderLineOutputDto();
        dto.setId(orderLine.getId());
        dto.setBeerId(orderLine.getBeer().getId());
        dto.setBeerName(orderLine.getBeer().getName());
        dto.setBeerType(orderLine.getBeer().getBeerCategoryType());
        dto.setAmount(orderLine.getQuantity());
        dto.setPriceAtPurchase(orderLine.getPriceAtPurchase());
        dto.setTotalPriceExcludingVat(orderLine.getTotalPriceExcludingVat());
        dto.setTotalPriceIncludingVat(orderLine.getTotalPriceIncludingVat());
        dto.setVatAmount(orderLine.getTotalPriceIncludingVat().subtract(orderLine.getTotalPriceExcludingVat()));

        return dto;
    }

    public static OrderLine transferToOrderLineEntity(OrderLineInputDto dto, Beer beer) {
        OrderLine orderLine = new OrderLine();
        orderLine.setBeer(beer);
        orderLine.setQuantity(dto.getQuantity());
        orderLine.setPriceAtPurchase(beer.getPrice());

        return orderLine;
    }
}