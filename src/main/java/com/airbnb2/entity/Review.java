package com.airbnb2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "review")
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content", length = 500)
    private String content;

    @ManyToOne
    @JoinColumn(name = "property_id" , referencedColumnName = "id" , columnDefinition = "INT")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "property_user_id" , referencedColumnName = "id")
    private PropertyUserEntity propertyUser;

    public PropertyUserEntity getPropertyUserEntity() {
        return propertyUser;
    }

    public void setPropertyUserEntity(PropertyUserEntity propertyUserEntity) {
        this.propertyUser = propertyUserEntity;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}