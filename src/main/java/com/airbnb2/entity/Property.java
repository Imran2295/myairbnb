package com.airbnb2.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "property")
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "property_name", nullable = false, length = 100)
    private String propertyName;

    @Column(name = "bedroom", nullable = false)
    private Integer bedroom;

    @Column(name = "no_of_bed", nullable = false)
    private Integer noOfBed;

    @Column(name = "bathroom", nullable = false)
    private Integer bathroom;

    @Column(name = "guest", nullable = false)
    private Integer guest;

    @Column(name = "nightly_price", nullable = false)
    private Integer nightlyPrice;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Integer getNightlyPrice() {
        return nightlyPrice;
    }

    public void setNightlyPrice(Integer nightlyPrice) {
        this.nightlyPrice = nightlyPrice;
    }

    public Integer getGuest() {
        return guest;
    }

    public void setGuest(Integer guest) {
        this.guest = guest;
    }

    public Integer getBathroom() {
        return bathroom;
    }

    public void setBathroom(Integer bathroom) {
        this.bathroom = bathroom;
    }

    public Integer getNoOfBed() {
        return noOfBed;
    }

    public void setNoOfBed(Integer noOfBed) {
        this.noOfBed = noOfBed;
    }

    public Integer getBedroom() {
        return bedroom;
    }

    public void setBedroom(Integer bedroom) {
        this.bedroom = bedroom;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "Property :\n" +
                "\npropertyName='" + propertyName +"\n"+
                "\nbedroom=" + bedroom +"\n"+
                "\nnoOfBed=" + noOfBed +"\n"+
                "\nbathroom=" + bathroom +"\n"+
                "\nguest=" + guest +"\n"+
                "\nnightlyPrice=" + nightlyPrice +"\n"+
                "\n"+location +"\n"+
                "\n"+country+".";
    }
}