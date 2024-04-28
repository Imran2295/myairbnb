package com.airbnb2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "images")
public class Images {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "image_url", nullable = false)
    private String imageUrl;

    @Column(name = "image_key" , nullable = false)
    private String imageKey;

    @ManyToOne
    @JoinColumn(name = "property_id" , columnDefinition = "INT")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "property_user_id")
    private PropertyUserEntity propertyUser;


    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PropertyUserEntity getPropertyUser() {
        return propertyUser;
    }

    public void setPropertyUser(PropertyUserEntity propertyUser) {
        this.propertyUser = propertyUser;
    }

    public String getImageKey() {
        return imageKey;
    }

    public void setImageKey(String imageKey) {
        this.imageKey = imageKey;
    }
}