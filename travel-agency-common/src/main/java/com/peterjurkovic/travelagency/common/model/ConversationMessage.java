package com.peterjurkovic.travelagency.common.model;


import java.time.Instant;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class ConversationMessage {

    public enum Type{USER, EVENT}
    
    @Id
    private String id;
    
    @JsonIgnore
    @Indexed 
    @DBRef 
    private Conversation conversation;
    
    @JsonIgnore
    private String participantId;
    
    private String content;
    
    private Instant created = Instant.now();
    
    private Type type = Type.USER;
    
    
    public ConversationMessage() {}
    
    public ConversationMessage(Conversation conversation) {
        this.conversation = conversation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Conversation getConversation() {
        return conversation;
    }

    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
    
    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

     
    public Instant getCreated() {
        return created;
    }

    public void setCreated(Instant created) {
        this.created = created;
    }

    @Transient
    public Participant getParticipant(){
        if(conversation != null)
           return conversation.getParticipants().stream()
                            .filter( p -> p.getId().equals(participantId))
                            .findFirst()
                            .orElse( null );
        return null;
    }
    
    
}
