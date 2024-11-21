package com.backend.beer_api_application.exceptions;

public class ImageNotFoundException extends RuntimeException {

    public ImageNotFoundException(String message) {
        super(message);
    }

    public ImageNotFoundException(Long imageId) {
        super("Image with ID " + imageId + " not found");
    }
}
