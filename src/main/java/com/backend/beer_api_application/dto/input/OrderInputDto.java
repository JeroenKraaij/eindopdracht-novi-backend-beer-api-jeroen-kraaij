package com.backend.beer_api_application.dto.input;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class OrderInputDto {

    @NotNull(message = "Customer ID is mandatory")
    private Long customerId;  // ID of the customer placing the order

    @NotEmpty(message = "Order must contain at least one order line")
    private List<@Valid @NotNull(message = "Order line cannot be null") OrderLineInputDto> orderLines;  // List of order line details

    // Optional delivery address field, can be null if no delivery is needed
    private String deliveryAddress;

    // Optional payment method field, ensure it's set based on business rules
    private String paymentMethod;
}
