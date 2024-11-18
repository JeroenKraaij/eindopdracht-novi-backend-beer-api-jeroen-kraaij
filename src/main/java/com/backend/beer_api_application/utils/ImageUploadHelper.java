package com.backend.beer_api_application.utils;

import com.backend.beer_api_application.models.Beer;
import com.backend.beer_api_application.models.ImageUpload;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HexFormat;

public class ImageUploadHelper {

    public static ImageUpload createImageUpload(Beer beer, MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String contentType = file.getContentType();
        long size = file.getSize();
        byte[] imageData = file.getBytes();

        ImageUpload image = new ImageUpload();
        image.setFileName(fileName);
        image.setImageData(imageData);
        image.setSize(size);
        image.setContentType(contentType);
        image.setUploadDate(LocalDateTime.now());
        image.setBeer(beer);
        image.setFileExtension(getFileExtension(fileName));
        image.setIsFeatured(beer.getImageUploads().isEmpty());

        try (InputStream inputStream = file.getInputStream()) {
            BufferedImage bufferedImage = ImageIO.read(inputStream);
            if (bufferedImage != null) {
                image.setWidth(bufferedImage.getWidth());
                image.setHeight(bufferedImage.getHeight());
            }
        }

        image.setHash(generateHash(imageData));

        return image;
    }

    public static String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return null;
    }

    private static String generateHash(byte[] data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(data);
            return HexFormat.of().formatHex(hash);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Unable to hash image data", e);
        }
    }
}
