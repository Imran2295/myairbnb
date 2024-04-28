package com.airbnb2.repository;

import com.airbnb2.entity.Images;
import com.airbnb2.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImagesRepository extends JpaRepository<Images, Long> {


    Images findByIdAndPropertyId(long id , long propertyId);

    List<Images> findByProperty(Property property);
}