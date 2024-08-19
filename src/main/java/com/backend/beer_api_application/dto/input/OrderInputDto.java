package com.backend.beer_api_application.dto.input;

import lombok.Data;

@Data
public class OrderInputDto {

    private Long customerId;
    private Long beerId;
    private Integer quantity;

    public OrderInputDto() {}

    public OrderInputDto(Long customerId, Long beerId, Integer quantity) {
        this.customerId = customerId;
        this.beerId = beerId;
        this.quantity = quantity;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getId() {
        return beerId;
    }

    public void setBeerId(Long beerId) {
        this.beerId = beerId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}
