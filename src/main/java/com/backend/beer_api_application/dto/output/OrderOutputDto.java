package com.backend.beer_api_application.dto.output;


import java.util.ArrayList;
import java.util.List;

public class OrderOutputDto {

    public Long id;

    public List<OrderLineOutputDto> orderLineOutputDtoList = new ArrayList<>();
    public List<CustomerOutputDto> customerOutputDtoList = new ArrayList<>();
}
