package com.annayoungyeun.days.models;

import javax.persistence.*;

@Entity
public class Bundle {

    //fields
    @Id
    @GeneratedValue
    private int id;

    private String bundleText;

    @ManyToOne
    @PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
    private User user;

    //constructors
    public Bundle(String bundleText, User user) {
        this.bundleText = bundleText;
        this.user = user;
    }

    public Bundle(){   }

    //getters & setters

    public int getId() {   return id;   }

    public void setId(int id) {   this.id = id;   }

    public String getBundleText() {   return bundleText;   }

    public void setBundleText(String bundleText) {   this.bundleText = bundleText;   }

    public User getUser() {   return user;   }

    public void setUser(User user) {   this.user = user;   }

}
