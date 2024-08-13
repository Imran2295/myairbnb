package com.airbnb2.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AWSS3Config {

    @Value("${accessKey}")
    private String accessKey; // initializes from values present in application.properties file.

    @Value("${secretKey}")
    private String secretKey; // initializes from values present in application.properties file.

    @Value("${region}")
    private String region; // initializes from values present in application.properties file.

    public AWSCredentials credentials() {
        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        return credentials;
    }
    // this method is taking the accessKey & secretKey for creating a credential reference AWS login.


    @Bean
    public AmazonS3 amazonS3() {
        AmazonS3 s3client = AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials())).withRegion(region).build();
        return s3client;
    }
    // this method used to login to the AWS account where we want to upload the files.

}
