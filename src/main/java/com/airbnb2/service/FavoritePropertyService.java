package com.airbnb2.service;

import com.airbnb2.entity.FavoriteProperty;
import com.airbnb2.entity.Property;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.exception.RecordNotFoundException;
import com.airbnb2.repository.FavoritePropertyRepository;
import com.airbnb2.repository.PropertyRepository;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FavoritePropertyService {

    private FavoritePropertyRepository favoritePropertyRepository;

    private PropertyRepository propertyRepository;

    public FavoritePropertyService(FavoritePropertyRepository favoritePropertyRepository, PropertyRepository propertyRepository) {
        this.favoritePropertyRepository = favoritePropertyRepository;
        this.propertyRepository = propertyRepository;
    }


    public FavoriteProperty addFavoriteProperty(PropertyUserEntity user, Long propertyId) {
        Property property = propertyRepository.findById(propertyId).orElseThrow(() ->
                new RecordNotFoundException("No Property is Found"));

        FavoriteProperty favoriteProperty = favoritePropertyRepository.findByPropertyUserAndProperty(user, property);
        if (favoriteProperty == null) { // Check if the favorite property doesn't exist yet
            favoriteProperty = new FavoriteProperty();
            favoriteProperty.setProperty(property);
            favoriteProperty.setPropertyUser(user);
            favoriteProperty.setIsFavorite(true); // Set it as favorite
            return favoritePropertyRepository.save(favoriteProperty);
        } else if (!favoriteProperty.getIsFavorite()) { // Check if it's not already a favorite
            favoriteProperty.setIsFavorite(true); // Set it as favorite
            return favoritePropertyRepository.save(favoriteProperty);
        } else {
            // Property is already a favorite, no need to change anything
            favoriteProperty.setIsFavorite(false);
            return favoriteProperty;
        }
    }

    public Boolean removeFavorite(PropertyUserEntity user, Property property) {

        FavoriteProperty favoriteProperty = favoritePropertyRepository.findByPropertyUserAndProperty(user, property);
        if(favoriteProperty.getIsFavorite()){
            favoriteProperty.setIsFavorite(false);
            favoritePropertyRepository.save(favoriteProperty);
            return true;
        }
        return false;
    }


    @Transactional
    public Boolean deleteFavorite(PropertyUserEntity user , Long propertyId){
        Property property = propertyRepository.findById(propertyId).orElseThrow(() ->
                new RecordNotFoundException("no Property Found"));

        favoritePropertyRepository.deleteByPropertyUserAndProperty(user, property);
        return true;
    }



    public List<FavoriteProperty> findAllFavorite(PropertyUserEntity user) {
        // getting all properties of that user.
        List<FavoriteProperty> favoritePropertyList = favoritePropertyRepository.findByPropertyUser(user);

        // filtering favorite properties by using stream filter method.
        //List<FavoriteProperty> collect = favoritePropertyList.stream().filter((FavoriteProperty::getIsFavorite)).collect(Collectors.toList());

        // Alternate way without using method reference using lambda instead
        List<FavoriteProperty> favoriteProperties = favoritePropertyList.stream().filter(fp -> fp.getIsFavorite()).collect(Collectors.toList());

        return favoriteProperties;
    }

}
