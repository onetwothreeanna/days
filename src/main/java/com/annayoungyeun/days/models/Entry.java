package com.annayoungyeun.days.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 * Created by AnnaYoungyeun.
 */

@Entity
public class Entry {

    //fields
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3, max=300)
    private String entryText;

//    private String date;
//
//    @ManyToOne
//    private User user;
//
//    @ManyToOne
//    private Chronicle chronicle;

    //constructors
    public Entry(String entryText) {this.entryText = entryText;}

    public Entry(){  }

    //getters + setters

    public int getId() {   return id;   }

    public void setId(int id) {   this.id = id;   }

    public String getEntryText() {   return entryText;   }

    public void setEntryText(String entryText) {   this.entryText = entryText;   }

//    public String getDate() {   return date;   }
//
//    public void setDate(String date) {   this.date = date;   }
//
//    public User getUser() {   return user;   }
//
//    public void setUser(User user) {   this.user = user;   }


}
