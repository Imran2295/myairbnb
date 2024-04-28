package com.airbnb2.repository;

import com.airbnb2.entity.Property;
import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewRepository extends JpaRepository<Review, Long> {


    @Query("SELECT r FROM Review r WHERE r.property.id = :propertyId AND r.propertyUser.id = :userId")
    Review findReviewByPropertyIdAndUserId(@Param("propertyId") Long propertyId , @Param("userId") Long UserId);

    // Above JPQL Query method i can write like this Also: using object instead of only id.
    //@Query("SELECT r FROM Review r WHERE r.property = :property AND r.propertyUser = :user")
    //Review findReviewByPropertyAndUser(@Param("property") Property property , @Param("user") PropertyUserEntity user);

    @Query("SELECT r " +
            "FROM Review r " +
            "WHERE r.propertyUser = :user")
    List<Review> findAllReviewsByUser(@Param("user") PropertyUserEntity user);



    @Query(" SELECT u.firstName , c.countryName , l.locationName , r.content " +
            " FROM Review r " +
            "JOIN PropertyUserEntity u ON r.propertyUser = u.id " +
            "JOIN Property p ON r.property = p.id " +
            "JOIN Country c ON p.country = c.id " +
            "JOIN Location l ON p.location = l.id " +
            "WHERE p.id = :id " )
    List<Object[]> findAllReviews(@Param("id") Long id);

}