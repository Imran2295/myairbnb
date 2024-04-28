package com.airbnb2.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "booking")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    @Column(name = "total_nights", nullable = false)
    private Integer totalNights;

    @Column(name = "guest_name", nullable = false)
    private String guestName;

    @ManyToOne
    @JoinColumn(name = "property_id" , columnDefinition = "INT")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "property_user_id")
    private PropertyUserEntity propertyUser;

    @Column(name = "check_in", nullable = false)
    private LocalDateTime checkIn;

    @Column(name = "check_out", nullable = false)
    private LocalDateTime checkOut;

    public LocalDateTime getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(LocalDateTime checkIn) {
        this.checkIn = checkIn;
    }

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    public String getGuestName() {
        return guestName;
    }

    public void setGuestName(String guestName) {
        this.guestName = guestName;
    }

    public Integer getTotalNights() {
        return totalNights;
    }

    public void setTotalNights(Integer totalNights) {
        this.totalNights = totalNights;
    }

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
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

    public LocalDateTime getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(LocalDateTime checkOut) {
        this.checkOut = checkOut;
    }


    @Override
    public String toString() {

        return  "\nBooked By: "+ propertyUser +
                "\n"+ property +"\n"+
                "\ntotalPrice: " + totalPrice +"\n"+
                "\ntotalNights: " + totalNights +"\n"+
                "\nguestName: " + guestName +"\n"+
                "\ncheckIn: " + checkIn +"\n"+
                "\ncheckOut: " + checkOut +"\n";
    }
}