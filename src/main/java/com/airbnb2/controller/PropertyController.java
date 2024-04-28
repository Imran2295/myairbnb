package com.airbnb2.controller;

import com.airbnb2.entity.Property;
import com.airbnb2.repository.PropertyRepository;
import com.airbnb2.service.PropertyService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v2/property")
public class PropertyController {

    private PropertyService propertyService;

    public PropertyController(PropertyService propertyService) {
        this.propertyService = propertyService;
    }

    // http://localhost:8080/api/v2/property
    @GetMapping("/{location}")
    public ResponseEntity<List<Property>> getAllProperty(@PathVariable String location){

        List<Property> propertyList = propertyService.getAllPropertyByLocation(location);

        return new ResponseEntity<>(propertyList , HttpStatus.OK);
    }


    // http://localhost:8080/api/v2/property/by-name/
    @GetMapping("/by-name/{query}")
    public ResponseEntity<?> getAllPropertyByName(@PathVariable String query){

        List<Property> properties = propertyService.getAllPropertyByName(query);

        return new ResponseEntity<>(properties , HttpStatus.OK);
    }
}