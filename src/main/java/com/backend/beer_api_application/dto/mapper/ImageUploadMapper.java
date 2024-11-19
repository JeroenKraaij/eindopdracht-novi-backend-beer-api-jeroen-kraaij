package com.backend.beer_api_application.dto.mapper;

import com.backend.beer_api_application.dto.output.ImageUploadOutputDto;
import com.backend.beer_api_application.models.ImageUpload;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class ImageUploadMapper {

    @Value("${file.base-url}")
    private String fileBaseUrl;

    public ImageUploadOutputDto toOutputDto(ImageUpload imageUpload) {
        ImageUploadOutputDto dto = new ImageUploadOutputDto();
        dto.setId(imageUpload.getId());
        dto.setFileName(imageUpload.getFileName());
        dto.setUrl(fileBaseUrl + "/" + imageUpload.getFileName()); // Constructs the accessible URL
        dto.setSize(imageUpload.getSize());
        dto.setContentType(imageUpload.getContentType());
        dto.setUploadDate(imageUpload.getUploadDate());
        dto.setAltText(imageUpload.getAltText());
        dto.setWidth(imageUpload.getWidth());
        dto.setHeight(imageUpload.getHeight());
        dto.setHash(imageUpload.getHash());
        dto.setIsFeatured(imageUpload.getIsFeatured());
        dto.setStatus(imageUpload.getStatus());
        dto.setFileExtension(imageUpload.getFileExtension());
        dto.setBeerId(imageUpload.getBeer().getId());

        return dto;
    }
}
