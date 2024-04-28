package com.airbnb2.repository;

import com.airbnb2.entity.Property;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PropertyRepository extends JpaRepository<Property, Long> {



    @Query("SELECT p " +
            "FROM Property p " +
            "JOIN Location l ON p.location = l.id " +
            "JOIN Country c ON p.country = c.id " +
            "WHERE l.locationName = :location OR c.countryName = :location")
    public List<Property> findPropertyByLocation(@Param("location") String location);



    @Query("SELECT p FROM Property p WHERE p.propertyName LIKE CONCAT ('%',:query,'%') " +
            " OR p.nightlyPrice LIKE CONCAT ('%',:query,'%') ")
    public List<Property> searchRecords(String query);

}