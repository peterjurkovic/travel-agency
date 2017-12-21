package com.peterjurkovic.travelagency.common.model;

import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document
@Data public class Trip {
    
    @Id
    private String id;
    private String title;
    private Country countryCode;
    private String description;
    private String content;
    private String avatarUrl;
    private Instant createdAt = Instant.now();
    private int purchases;
      
    @Override
    public String toString() {
        return "Trip [id=" + id + ", title=" + title + "]";
    }
    

}
