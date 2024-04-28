package com.airbnb2.repository;

import com.airbnb2.entity.FavoriteProperty;
import com.airbnb2.entity.Property;
import com.airbnb2.entity.PropertyUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface FavoritePropertyRepository extends JpaRepository<FavoriteProperty , Long> {


    public FavoriteProperty findByPropertyUserAndProperty(PropertyUserEntity propertyUser , Property property);


    public List<FavoriteProperty> findByPropertyUser(PropertyUserEntity propertyUser);


    void deleteByPropertyUserAndProperty(PropertyUserEntity user, Property property);

}
