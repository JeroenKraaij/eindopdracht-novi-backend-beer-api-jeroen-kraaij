package com.backend.beer_api_application.dto.input;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ImageUploadInputDto {
    private MultipartFile file;
    private String altText;
    private Boolean isFeatured;
}

