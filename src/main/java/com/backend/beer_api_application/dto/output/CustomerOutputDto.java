package com.backend.beer_api_application.dto.output;

import lombok.Data;

@Data
public class CustomerOutputDto {

    private Long id;
    private String firstname;
    private String surname;
    private String address;
    private String houseNumber;
    private String zipcode;
    private String city;
    private String phone;
    private String dateOfBirth;
    private String email;

}


