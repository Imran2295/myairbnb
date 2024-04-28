package com.airbnb2.controller;

import com.airbnb2.entity.Images;
import com.airbnb2.entity.Property;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.service.ImageService;
import com.airbnb2.service.S3BucketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v2/images")
public class ImageController {

    private ImageService imageService;

    private S3BucketService bucketService;

    public ImageController(ImageService imageService, S3BucketService bucketService) {
        this.imageService = imageService;
        this.bucketService = bucketService;
    }

    // http://localhost:8080/api/v2/images/saveImageUrl/myairbnb007/property/1
    @PostMapping("/saveImageUrl/{bucketName}/property/{propertyId}")
    public ResponseEntity<?> saveImageUrl(@RequestParam MultipartFile file,
                                          @PathVariable String bucketName,
                                          @PathVariable Long propertyId,
                                          @AuthenticationPrincipal PropertyUserEntity user
                                          ){
        String fileUrl = bucketService.uploadFile(file, bucketName);

        Images saveImageUrl = imageService.saveImageUrl(fileUrl, propertyId, user);

        return new ResponseEntity<>(saveImageUrl , HttpStatus.CREATED);
    }


    // http://localhost:8080/api/v2/images/delete-image/propertyId/1/imageId/1
    @DeleteMapping("/delete-image/propertyId/{propertyId}/imageId/{imageId}")
    public ResponseEntity<String> deleteImage(@PathVariable long propertyId,
                                              @PathVariable long imageId){

        String result = imageService.deleteImage(propertyId, imageId);

        if(result != null){
            return new ResponseEntity<>("FIle is successfully deleted from S3 bucket" , HttpStatus.OK);
        }
        return new ResponseEntity<>("Not Deleted something went wrong" , HttpStatus.INTERNAL_SERVER_ERROR);
    }


    // http://localhost:8080/api/v2/images/get-all-images/1
    @GetMapping("/get-all-images/{propertyId}")
    public ResponseEntity<?> getAllImages(@PathVariable Long propertyId){

        List<Images> allImages = imageService.getAllImages(propertyId);

        return new ResponseEntity<>(allImages , HttpStatus.OK);
    }


}



