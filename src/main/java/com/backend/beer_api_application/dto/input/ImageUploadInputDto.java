package com.backend.beer_api_application.dto.input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.Size;

@Data
public class ImageUploadInputDto {
    private MultipartFile file;

    @Size(max = 255)
    private String altText;

    private Boolean isFeatured;
}