package com.airbnb2.service;

import com.airbnb2.dto.MyComponent;
import com.airbnb2.entity.Images;
import com.airbnb2.entity.Property;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.exception.RecordNotFoundException;
import com.airbnb2.repository.ImagesRepository;
import com.airbnb2.repository.PropertyRepository;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ImageService {

    private ImagesRepository imagesRepository;

    private PropertyRepository propertyRepository;

    private AmazonS3 amazonS3;

    private MyComponent component;

    public ImageService(ImagesRepository imagesRepository, PropertyRepository propertyRepository, AmazonS3 amazonS3, MyComponent component) {
        this.imagesRepository = imagesRepository;
        this.propertyRepository = propertyRepository;
        this.amazonS3 = amazonS3;
        this.component = component;
    }

    public Images saveImageUrl(String url , Long propertyId , PropertyUserEntity user){
        Property property = propertyRepository.findById(propertyId).orElseThrow(() ->
                new RecordNotFoundException("No property Exist with this id : " + propertyId));

        String imageKey = url.substring(url.lastIndexOf("/") + 1);

        Images images = new Images();
        images.setImageUrl(url);
        images.setImageKey(imageKey);
        images.setProperty(property);
        images.setPropertyUser(user);

        Images savedImage = imagesRepository.save(images);
        return savedImage;
    }


    public String deleteImage(long propertyId, long imageId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() ->
                new RecordNotFoundException("No Property Found"));

        Images image = imagesRepository.findByIdAndPropertyId(imageId, propertyId);
        if(image != null){
            imagesRepository.deleteById(imageId);
        }
        else {
            throw new RecordNotFoundException("No image present on this property");
        }
        try {
            amazonS3.deleteObject(new DeleteObjectRequest(component.getBucketName() , image.getImageKey()));
            return "deleted";
        }catch (Exception e){
            return null;
        }
    }


    public List<Images> getAllImages(Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(()
                -> new RecordNotFoundException("No Property Present"));

        List<Images> imagesList = imagesRepository.findByProperty(property);

        return imagesList;
    }
}
