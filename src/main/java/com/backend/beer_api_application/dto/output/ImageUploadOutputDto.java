package com.backend.beer_api_application.dto.output;

import com.backend.beer_api_application.enums.ImageStatus;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ImageUploadOutputDto {
    private Long id;
    private String fileName;
    private String url;
    private Long size;
    private String contentType;
    private LocalDateTime uploadDate;
    private String altText;
    private Integer width;
    private Integer height;
    private String hash;
    private Boolean isFeatured;
    private ImageStatus status;
    private String fileExtension;
    private Long beerId;
}
