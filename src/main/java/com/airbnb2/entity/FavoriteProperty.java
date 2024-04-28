package com.airbnb2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "favorite_property")
public class FavoriteProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;



    @ManyToOne
    @JoinColumn(name = "property_user_id")
    private PropertyUserEntity propertyUser;

    @ManyToOne
    @JoinColumn(name = "property_id" , columnDefinition = "INT")
    private Property property;

    @Column(name = "is_favorite", nullable = false)
    private Boolean isFavorite = false;


    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }


    public Boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(Boolean isFavorite) {
        this.isFavorite = isFavorite;
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

}