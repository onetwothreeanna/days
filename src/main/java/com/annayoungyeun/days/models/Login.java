package com.annayoungyeun.days.models;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Login {

    //fields
    @NotNull
    @Size(min=4, max=15, message = "Please enter a username.")
    private String username;

    @NotNull
    @Size(min=6, message = "Please enter a password.")
    private String password;

    //constructors
    public Login(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public Login(){  }

    //getters & setters
    public String getUsername() {   return username;   }

    public void setUsername(String username) {   this.username = username;   }

    public String getPassword() {   return password;   }

    public void setPassword(String password) {   this.password = password;   }
}


