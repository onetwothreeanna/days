package com.annayoungyeun.days.models;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

public class SettingsForm {

    @NotNull
    private String theme;

    @NotNull
    private String notifications;

    @Email
    @NotEmpty
    private String email;

    //constructor

    public SettingsForm(String theme, String notifications, String email) {
        this.theme = theme;
        this.notifications = notifications;
        this.email = email;
    }

    public SettingsForm(){   }

    //getters + setters


    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getNotifications() {
        return notifications;
    }

    public void setNotifications(String notifications) {
        this.notifications = notifications;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
