package com.peterjurkovic.travelagency.common.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Trip {
    
    @Id
    private String id;
    private String title;
    private Country countryCode;
    private String description;
    private String content;
    private String avatarUrl;
    private Instant createdAt;
    private int purchases;
    
    public Trip(){
        this.createdAt = Instant.now();
    }
  
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
        
    public Country getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(Country countryCode) {
        this.countryCode = countryCode;
    }
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public int getPurchases() {
        return purchases;
    }

    public void setPurchases(int purchases) {
        this.purchases = purchases;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public String toString() {
        return "Trip [id=" + id + ", title=" + title + "]";
    }
    
    
    

}
