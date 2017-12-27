package com.peterjurkovic.travelagency.common.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Conversation {

    public enum Status {ACTIVE, ARCHIVED}
    
    @Id 
    private String id;
    private Instant date = Instant.now();
    private Status status = Status.ACTIVE;
    
    @Indexed
    private List<Participant> participants = new ArrayList<>(4);

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant){
        this.participants.add(participant);
    }

    @Override
    public String toString() {
        return "Conversation [id=" + id + ", date=" + date + ", participants=" + participants + "]";
    }
    
    
}
