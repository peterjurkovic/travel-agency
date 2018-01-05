package com.peterjurkovic.travelagency.common.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import com.peterjurkovic.travelagency.common.utils.JsonUtils;

@Document
public class AdminUser {
    
    @Id private String id;
    private String email;
    private String phone;
    private String password;
    private String firstName;
    private String lastName;
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
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    @Override
    public String toString() {
        return "AdminUser [email=" + email + ", phone=" + phone + "]";
    }
    
    @Transient
    public String getFullName(){
        return this.firstName + " " + this.getLastName();
    }
    
    @Transient
    public Participant toParticipant(){
        Participant participant = new Participant();
        participant.setId(this.getId());
        participant.setName(this.getFullName());
        participant.setType(ParticipantType.AGENT);
        participant.setPhoneNumber(this.getPhone());
        return participant;
    }
}
