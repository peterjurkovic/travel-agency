package com.peterjurkovic.travelagency.common.model;


import java.time.Instant;
import java.util.Optional;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document
public class ConversationMessage {

    public enum Type{USER, EVENT, SMS}
    
    @Id
    private String id;
    
    @JsonIgnore
    @Indexed 
    @DBRef 
    private Conversation conversation;
    
    @JsonIgnore
    private String participantId;
    
    private String content;
    
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Instant created = Instant.now();
    
    private Type type = Type.USER;
    
    
    public ConversationMessage() {}
    
    public ConversationMessage(Conversation conversation) {
        this.conversation = conversation;
    }
    
    public ConversationMessage(Conversation conversation, Participant participant) {
        this(conversation);
        this.participantId = participant.getId();
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
    public boolean isUserMessage(){
        Participant participant = getParticipant();
        if(participant != null && participant.isUser()){
            return true;
        }
        return false;
    }
    
    @Transient
    public boolean isBotMessage(){
        Participant participant = getParticipant();
        if(participant != null && participant.isBot()){
            return true;
        }
        return false;
    }
    
    @Transient
    public String getParticipantName(){
        if(conversation != null)
            return conversation.findParticipantById(participantId).get().getName();
        
        return "";
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
    
    
    @Transient
    public Optional<String> getParticipantPhoneNumber(){
        Participant participant = getParticipant();
        if(participant != null){
            return Optional.ofNullable(participant.getPhoneNumber());
        }
        return Optional.empty();
    }
    
}
