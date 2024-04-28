package com.airbnb2.repository;

import com.airbnb2.entity.PropertyUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PropertyUserRepository extends JpaRepository<PropertyUserEntity , Long> {

    Optional<PropertyUserEntity> findByUsername(String username);
}
