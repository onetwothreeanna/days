package com.annayoungyeun.days.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Year;
import java.util.ArrayList;
import java.util.List;

public class Chronicle {

    //fields
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    private Year year;

    @OneToMany
    @JoinColumn(name = "chronicle_id")
    private List<Entry> entries = new ArrayList<>();

    @ManyToOne
    private User user;

    //constructors
    public Chronicle(Year year, List<Entry> entries) {
        this.year = year;
        this.entries = entries;
    }

    public Chronicle(){   }

    //getters + setters

    public int getId() {   return id;   }

    public void setId(int id) {   this.id = id;   }

    public Year getYear() {   return year;   }

    public void setYear(Year year) {   this.year = year;   }

    public List<Entry> getEntries() {   return entries;   }

    public void setEntries(List<Entry> entries) {   this.entries = entries;   }

    public User getUser() {   return user;   }

    public void setUser(User user) {   this.user = user;   }


}
