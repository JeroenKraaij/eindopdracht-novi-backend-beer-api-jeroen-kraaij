package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.ImageUploadInputDto;
import com.backend.beer_api_application.dto.output.ImageUploadOutputDto;
import com.backend.beer_api_application.dto.mapper.ImageUploadMapper;
import com.backend.beer_api_application.exceptions.RecordNotFoundException;
import com.backend.beer_api_application.models.ImageUpload;
import com.backend.beer_api_application.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "api/v1")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private ImageUploadMapper imageUploadMapper;

    @PostMapping("/images/upload/{beerId}")
    public ResponseEntity<Object> uploadImage(@PathVariable Long beerId, @ModelAttribute ImageUploadInputDto imageUploadInputDto) {
        try {
            ImageUploadOutputDto outputDto = imageUploadMapper.toOutputDto(
                    imageUploadService.uploadImage(beerId, imageUploadInputDto.getFile())
            );
            return ResponseEntity.ok(outputDto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file upload: " + e.getMessage());
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Beer not found with ID: " + beerId);
        }
    }

    @PostMapping("/images/upload/multiple/{beerId}")
    public ResponseEntity<Object> uploadMultipleImages(@PathVariable Long beerId, @RequestParam("files") MultipartFile[] files) {
        try {
            List<ImageUploadOutputDto> outputDtos = imageUploadService.uploadMultipleImages(beerId, files).stream()
                    .map(imageUploadMapper::toOutputDto)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(outputDtos);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing multiple file upload: " + e.getMessage());
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Beer not found with ID: " + beerId);
        }
    }

    @PutMapping("/images/{imageId}")
    public ResponseEntity<Object> updateImage(@PathVariable Long imageId, @ModelAttribute ImageUploadInputDto imageUploadInputDto) {
        try {
            ImageUploadOutputDto outputDto = imageUploadMapper.toOutputDto(
                    imageUploadService.updateImage(imageId, imageUploadInputDto.getFile())
            );
            return ResponseEntity.ok(outputDto);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error processing file update: " + e.getMessage());
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found with ID: " + imageId);
        }
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Object> deleteImage(@PathVariable Long imageId) {
        try {
            imageUploadService.deleteImage(imageId);
            return ResponseEntity.noContent().build();
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found with ID: " + imageId);
        }
    }

    @GetMapping("/images/{imageId}/download")
    public ResponseEntity<Object> downloadImage(@PathVariable Long imageId) {
        try {
            ImageUpload imageUpload = imageUploadService.getImageById(imageId);

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(imageUpload.getContentType()))
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageUpload.getFileName() + "\"")
                    .body(imageUpload.getImageData());
        } catch (RecordNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Image not found with ID: " + imageId);
        }
    }
}
