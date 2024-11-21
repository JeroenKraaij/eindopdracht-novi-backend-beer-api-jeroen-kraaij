package com.backend.beer_api_application.controller;

import com.backend.beer_api_application.dto.input.ImageUploadInputDto;
import com.backend.beer_api_application.dto.output.ImageUploadOutputDto;
import com.backend.beer_api_application.dto.mapper.ImageUploadMapper;
import com.backend.beer_api_application.models.ImageUpload;
import com.backend.beer_api_application.services.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping(value = "api/v1")
public class ImageUploadController {

    @Autowired
    private ImageUploadService imageUploadService;

    @Autowired
    private ImageUploadMapper imageUploadMapper;

    @PostMapping("/images/upload/{beerId}")
    public ResponseEntity<ImageUploadOutputDto> uploadImage(@PathVariable Long beerId, @ModelAttribute ImageUploadInputDto imageUploadInputDto) throws IOException {
        ImageUploadOutputDto outputDto = imageUploadMapper.toOutputDto(
                imageUploadService.uploadImage(beerId, imageUploadInputDto.getFile())
        );
        return ResponseEntity.ok(outputDto);
    }

    @PutMapping("/images/{imageId}")
    public ResponseEntity<ImageUploadOutputDto> updateImage(@PathVariable Long imageId, @ModelAttribute ImageUploadInputDto imageUploadInputDto) throws IOException {
        ImageUploadOutputDto outputDto = imageUploadMapper.toOutputDto(
                imageUploadService.updateImage(imageId, imageUploadInputDto.getFile())
        );
        return ResponseEntity.ok(outputDto);
    }

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long imageId) {
        imageUploadService.deleteImage(imageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/images/{imageId}/download")
    public ResponseEntity<byte[]> downloadImage(@PathVariable Long imageId) {
        ImageUpload imageUpload = imageUploadService.getImageById(imageId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(imageUpload.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + imageUpload.getFileName() + "\"")
                .body(imageUpload.getImageData());
    }
}
