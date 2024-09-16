package com.backend.beer_api_application.dto.input;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerInputDto {

    @NotBlank(message = "First name is mandatory")
    @Size(max = 100, message = "First name can be at most 100 characters")
    private String firstname;

    @NotBlank(message = "Surname is mandatory")
    @Size(max = 100, message = "Surname can be at most 100 characters")
    private String surname;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 255, message = "Address can be at most 255 characters")
    private String address;

    @NotBlank(message = "House number is mandatory")
    @Size(max = 10, message = "House number can be at most 10 characters")
    private String houseNumber;

    @NotBlank(message = "Zipcode is mandatory")
    @Size(max = 10, message = "Zipcode can be at most 10 characters")
    private String zipcode;

    @NotBlank(message = "City is mandatory")
    @Size(max = 100, message = "City can be at most 100 characters")
    private String city;

    @Pattern(regexp = "^[+]?[0-9]{2}-[0-9]{7,13}$", message = "Phone number should be valid")
    @Size(max = 15, message = "Phone number can be at most 15 characters")
    private String phone;

    @Past(message = "Date of birth must be in the past")
    private LocalDate dateOfBirth;
}
