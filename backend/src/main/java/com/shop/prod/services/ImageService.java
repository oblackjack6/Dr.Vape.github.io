package com.shop.prod.services;

import com.shop.prod.utils.ImageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class ImageService {

    @Value("${images.path}")
    private String uploadPath;

    @Value("${images.url}")
    private String imagesUrl;

    public ResponseEntity<String> uploadImage(MultipartFile image, String previousFile)  {
        try{
            Path path = Paths.get(uploadPath);
            if(Files.notExists(path)){
                Files.createDirectories(path);
            }

            String filename = UUID.randomUUID() + ImageConverter.OUTPUT_TYPE;
            Path savePath = Paths.get(uploadPath, filename);

            InputStream jpgImage = ImageConverter.convertToJpg(image);
            Files.copy(jpgImage, savePath);

            if(previousFile != null){
                previousFile = urlToName(previousFile);
                deleteImage(previousFile);
            }

            return new ResponseEntity<>(nameToUrl(filename), HttpStatus.OK);
        } catch (Exception e) {
            throw new RuntimeException("Failed while adding new image!");
        }
    }

    public ResponseEntity<String> deleteImage(String filename) {
        filename = urlToName(filename);
        try {
            Path filePath = Paths.get(uploadPath, filename).normalize();

            if (!filePath.startsWith(Paths.get(uploadPath).normalize())) {
                throw new RuntimeException("Invalid path");
            }

            if (Files.notExists(filePath)) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("File: " + filename + " not found!");
            }

            Files.delete(filePath);

            return ResponseEntity.ok("File: " + filename + " was successfully deleted");

        }catch (RuntimeException e){
            throw e;
        }
        catch (Exception e) {
            throw new RuntimeException("Failed while deleting image " + filename + " !");
        }
    }

    private String nameToUrl(String name){
        return imagesUrl+name;
    }

    private String urlToName(String url){
        if(!url.contains("/")){
            return url;
        }
        else {
            String[] parts = url.split("/");
            return parts[parts.length-1];
        }
    }
}
