package com.airbnb2.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.DeleteBucketRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

@Service
public class S3BucketService {

    @Autowired
    private AmazonS3 amazonS3; // use to upload the file into the S3 Bucket in AWS.

    public String uploadFile(MultipartFile file, String bucketName) {
        // checking if multipart file has anything in it or not.
        if (file.isEmpty()) {
            throw new IllegalStateException("Cannot upload empty file");
        }
        try {
            // converting the Multipart file to binary file and transferring it to the destination.
            File convFile = new File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
            file.transferTo(convFile);
            try {
                // uploading the file into the S3 bucket
                amazonS3.putObject(bucketName, convFile.getName(), convFile);
                // after uploading returning the Proper URL of uploaded file.
                return amazonS3.getUrl(bucketName, file.getOriginalFilename()).toString();
            } catch (AmazonS3Exception s3Exception) {
                return "Unable to upload file :" + s3Exception.getMessage();
            }
        } catch (Exception e) {
            throw new IllegalStateException("Failed to upload file", e);
        }

    }

    public MultipartFile convert(String filePath) throws IOException{
        File file = new File(filePath);

        byte[] fileContent = Files.readAllBytes(file.toPath());

        Resource resource = new ByteArrayResource(fileContent);

        MultipartFile multipartFile = new MultipartFile() {
            @Override
            public String getName() {
                return file.getName();
            }

            @Override
            public String getOriginalFilename() {
                return file.getName();
            }

            @Override
            public String getContentType() {
                return null;
            }

            @Override
            public boolean isEmpty() {
                return fileContent.length == 0;
            }

            @Override
            public long getSize() {
                return fileContent.length;
            }

            @Override
            public byte[] getBytes() throws IOException {
                return fileContent;
            }

            @Override
            public InputStream getInputStream() throws IOException {
                return resource.getInputStream();
            }

            @Override
            public void transferTo(File dest) throws IOException, IllegalStateException {
                Files.write(dest.toPath() , fileContent);
            }
        };
        return multipartFile;
    }
}
