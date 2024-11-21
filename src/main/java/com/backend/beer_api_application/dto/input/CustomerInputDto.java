package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerInputDto {

    @NotBlank
    @Size(max = 100)
    private String firstname;

    @NotBlank
    @Size(max = 100)
    private String surname;

    @NotBlank
    @Size(max = 255)
    private String address;

    @NotBlank
    @Size(max = 10)
    private String houseNumber;

    @NotBlank
    @Size(max = 10)
    private String zipcode;

    @NotBlank
    @Size(max = 100)
    private String city;

    @Pattern(regexp = "^[+]?[0-9]{2}-[0-9]{7,13}$")
    @Size(max = 15)
    private String phone;

    @Past
    private LocalDate dateOfBirth;
}