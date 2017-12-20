package com.peterjurkovic.travelagency.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class User{

    @Id
    private String id;
    
    private String email;
    private String password;
    private String fistName;
    private String lastName;

    
    public User(){}
    
    public User(String email, String password, String fistName, String lastName) {
        this.email = email;
        this.password = password;
        this.fistName = fistName;
        this.lastName = lastName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFistName() {
        return fistName;
    }

    public void setFistName(String fistName) {
        this.fistName = fistName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    
    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", name=" + fistName + ", lastName=" + lastName + "]";
    }
    
    

}
