package com.annayoungyeun.days.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Location {

    //fields
    @Id
    @GeneratedValue
    private int id;

    private String city;

    private String latitude;

    private String longitude;

    @ManyToOne
    private User user;

    //constructors
    public Location(int id, String city, String latitude, String longitude, User user) {
        this.id = id;
        this.city = city;
        this.latitude = latitude;
        this.longitude = longitude;
        this.user = user;
    }

    public Location(){   }

    //getters & setters

    public int getId() {   return id;   }

    public void setId(int id) {   this.id = id;   }

    public String getCity() {   return city;   }

    public void setCity(String city) {   this.city = city;   }

    public String getLatitude() {   return latitude;   }

    public void setLatitude(String latitude) {   this.latitude = latitude;   }

    public String getLongitude() {   return longitude;   }

    public void setLongitude(String longitude) {   this.longitude = longitude;   }

    public User getUser() {   return user;   }

    public void setUser(User user) {   this.user = user;   }
}
