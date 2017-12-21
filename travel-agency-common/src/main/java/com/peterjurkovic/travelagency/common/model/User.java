package com.peterjurkovic.travelagency.common.model;


import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document
public @Data class User{

    public enum Status{NEW, VERIFY, ACTIVE, BLOCKED}
    
    @Id
    private String id;
    private String email;
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
    private Status status = Status.NEW;

    @Override
    public String toString() {
        return "User [id=" + id + ", email=" + email + ", name=" + firstName + ", lastName=" + lastName + "]";
    }

}
