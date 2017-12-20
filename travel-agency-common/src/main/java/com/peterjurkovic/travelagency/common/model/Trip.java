package com.peterjurkovic.travelagency.common.model;

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
    
    
    public Trip() {}
    
    public Trip(String title, Country countryCode, String description, String content) {
        this.title = title;
        this.countryCode = countryCode;
        this.description = description;
        this.content = content;
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

    @Override
    public String toString() {
        return "Trip [id=" + id + ", title=" + title + "]";
    }
    
    
    

}
