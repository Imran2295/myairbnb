package com.airbnb2.service;

import com.airbnb2.entity.Property;
import com.airbnb2.repository.PropertyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropertyService {

    private PropertyRepository propertyRepository;

    public PropertyService(PropertyRepository propertyRepository) {
        this.propertyRepository = propertyRepository;
    }

    public List<Property> getAllPropertyByLocation(String location) {

        List<Property> properties = propertyRepository.findPropertyByLocation(location);
        return properties;
    }


    public List<Property> getAllPropertyByName(String query) {

        List<Property> propertyList = propertyRepository.searchRecords(query);

        return propertyList;
    }
}
