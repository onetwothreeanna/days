package com.annayoungyeun.days.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Archive {

    //fields
    @Id
    @GeneratedValue
    private int id;

    @OneToMany
    @JoinColumn(name = "archive_id")
    private List<Bundle> bundles = new ArrayList<>();

    @OneToOne
    private User user;

    //constructors
    public Archive(List<Bundle> bundles, User user) {
        this.bundles = bundles;
        this.user = user;
    }

    public Archive(){   }

    //getters + setters

    public int getId() {   return id;   }

    public void setId(int id) {   this.id = id;   }

    public List<Bundle> getBundles() {   return bundles;   }

    public void setBundles(List<Bundle> bundles) {   this.bundles = bundles;   }

    public User getUser() {   return user;   }

    public void setUser(User user) {   this.user = user;   }


}
