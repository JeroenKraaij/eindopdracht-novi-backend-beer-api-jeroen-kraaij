package com.backend.beer_api_application.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderInputDto {

    @NotNull
    private Long customerId;

    @NotEmpty
    private List<@Valid @NotNull OrderLineInputDto> orderLines;
    private String deliveryAddress;
    private String paymentMethod;
}