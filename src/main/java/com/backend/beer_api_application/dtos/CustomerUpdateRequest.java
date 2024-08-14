package com.backend.beer_api_application.dtos;

public record CustomerUpdateRequest(
        String firstname,
        String surname,
        String address,
        String houseNumber,
        String zipcode,
        String city,
        String email,
        String phone,
        String dateOfBirth
) {
}
