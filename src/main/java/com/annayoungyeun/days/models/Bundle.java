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

    @ManyToOne
    @PrimaryKeyJoinColumn(name="archive_id", referencedColumnName="id")
    private Archive archive;

    //constructors
    public Bundle(String bundleText, User user, Archive archive) {
        this.bundleText = bundleText;
        this.user = user;
        this.archive = archive;
    }

    public Bundle(){   }

    //getters & setters

    public int getId() {   return id;   }

    public void setId(int id) {   this.id = id;   }

    public String getBundleText() {   return bundleText;   }

    public void setBundleText(String bundleText) {   this.bundleText = bundleText;   }

    public User getUser() {   return user;   }

    public void setUser(User user) {   this.user = user;   }

    public Archive getArchive() {   return archive;   }

    public void setArchive(Archive archive) {   this.archive = archive;   }
}
