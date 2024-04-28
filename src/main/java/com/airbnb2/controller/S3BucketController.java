package com.airbnb2.controller;

import com.airbnb2.service.S3BucketService;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("s3bucket")
@CrossOrigin("*")
public class S3BucketController {

    @Autowired
    S3BucketService service;
    @Autowired
    private AmazonS3 amazonS3;


    // http://localhost:8080/s3bucket/upload/file/myairbnb007

    @PostMapping(path = "/upload/file/{bucketName}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> uploadFile(@RequestParam MultipartFile file,
                                             @PathVariable String bucketName) {
        return new ResponseEntity<>(service.uploadFile(file,bucketName), HttpStatus.OK);
    }
    // path attribute having the sub url of API
// consumes attribute consist of type of file is received like above it is a MediaType file.
// Multipart file is required to receive the file like images and text file etc.
// multipart file and bucket name we are sending to service layer for uploading that file to S3 bucket.



 // http://localhost:8080/s3bucket/delete-bucket/name
    @DeleteMapping("/delete-bucket/{bucketName}")
    public ResponseEntity<?> deleteAwsS3Bucket(@PathVariable String bucketName){

        try{
            amazonS3.deleteBucket(new DeleteBucketRequest(bucketName));
            return new ResponseEntity<>("bucket Deleted Successfully" , HttpStatus.OK);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error deleting Bucket "+ e.getMessage());
        }

    }
}

