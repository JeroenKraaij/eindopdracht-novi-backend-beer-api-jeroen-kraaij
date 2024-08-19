package com.backend.beer_api_application.dto.output;

import lombok.Data;
import java.util.List;

@Data
public class OrderOutputDto {

    private Long id;  // Order ID
    private String customerName;  // Name of the customer (e.g., "John Doe")
    private List<OrderLineOutputDto> orderLines;  // List of order line details
}
