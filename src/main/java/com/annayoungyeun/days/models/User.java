package com.annayoungyeun.days.models;
/**
 * Created by AnnaYoungyeun on 8/19/17.
 */
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {

    //fields
    @Id
    @GeneratedValue
    private int id;

    @NotNull
    @Size(min=3)
    private String username;

    @Email
    @NotEmpty
    private String email;

    @NotNull
    @Size(min=6)
    private String password;

    @NotNull
    @Transient
    private String verify;

    @OneToMany
    @JoinColumn(name = "user_id")
    private List<Entry> entries = new ArrayList<>();

    //constructors
    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {   }

    //getters + setters

    public int getId() {    return id;    }

    public void setId(int id) {    this.id = id;    }

    public String getUsername() {    return username;    }

    public void setUsername(String username) {    this.username = username;    }

    public String getEmail() {    return email;    }

    public void setEmail(String email) {    this.email = email;    }

    public String getPassword() {    return password;   }

    public void setPassword(String password) {   this.password = password;   }

    public String getVerify() {   return verify;   }

    public void setVerify(String verify) {   this.verify = verify;   }

    public List<Entry> getEntries() {    return entries;    }

    public void setEntries(List<Entry> entries) {    this.entries = entries;    }
}
