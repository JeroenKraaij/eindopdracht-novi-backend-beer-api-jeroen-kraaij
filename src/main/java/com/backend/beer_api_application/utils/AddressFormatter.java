package com.backend.beer_api_application.utils;

import org.springframework.stereotype.Service;

@Service
public class AddressFormatter {
    public static String formatAddress(String address, String houseNumber, String zipcode, String city) {
        return String.format("%s %s, %s %s", address, houseNumber, zipcode, city);
    }
}