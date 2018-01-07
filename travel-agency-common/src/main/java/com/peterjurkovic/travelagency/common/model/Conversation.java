package com.peterjurkovic.travelagency.common.model;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.util.StringUtils;

@Document
public class Conversation {

    public enum Status {ACTIVE, ARCHIVED}
    
    @Id 
    private String id;
    private Instant date = Instant.now();
    private String phoneNumber;
    private Status status = Status.ACTIVE;
    private int numberRequests;
    private int nameRequests;
    
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

           
    public int getNumberRequests() {
        return numberRequests;
    }

    public void setNumberRequests(int numberRequests) {
        this.numberRequests = numberRequests;
    }

    
    
    public int getNameRequests() {
        return nameRequests;
    }

    public void setNameRequests(int nameRequests) {
        this.nameRequests = nameRequests;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Transient
    public Optional<Participant> findParticipantById(String id){
        return participants.stream()
                .filter( p -> p.getId().equals(id))
                .findFirst();
    }
    
    @Transient
    public Optional<Participant> findParticipantByType(ParticipantType type){
        return participants.stream()
                .filter( p -> p.getType() == type)
                .findFirst();
    }
    
    @Transient
    public Optional<Participant> findParticipantByPhone(String phoneNumber){
        return participants.stream()
                .filter( p ->  phoneNumber.equals(p.getPhoneNumber()) )
                .findFirst();
    }
    
    @Transient
    public boolean hasPhoneNumberAssigned(){
        return StringUtils.hasText(this.phoneNumber);
    }
    @Transient
    public void incrementNumberInquires(){
        this.numberRequests++;
    }
    
    @Transient
    public void incrementNameInquires(){
        this.nameRequests++;
    }
    
    @Override
    public String toString() {
        return "Conversation [id=" + id + ", date=" + date + ", participants=" + participants + "]";
    }
    
    @Transient
    public boolean hasAssignedBot(){
        return participants.stream()
                    .filter(Participant::isBot)
                    .findFirst()
                    .isPresent();
    }
    
    @Transient
    public boolean hasAssignedAgent(){
        return participants.stream()
                    .filter(Participant::isAgent)
                    .findFirst()
                    .isPresent();
    }
    
    @Transient
    public Optional<Participant> getBot(){
        return this.participants.stream()
                    .filter(Participant::isBot)
                    .findFirst();
    }
    
    @Transient
    public String getUserName(){
        return this.participants.stream()
                    .filter(Participant::isUser)
                    .map(Participant::getName)
                    .findFirst()
                    .orElse("NOT ASSIGNED");
    }

    @Transient
    public String getAgentName(){
        return this.participants.stream()
                    .filter(Participant::isAgent)
                    .map(Participant::getName)
                    .findFirst()
                    .orElse("NOT ASSIGNED");
    }

    
    @Transient
    public void setUserPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
        Optional<Participant> user = findParticipantByType(ParticipantType.USER);
        if(user.isPresent()){
            user.get().setPhoneNumber(phoneNumber);
        }
    }
    
    
    @Transient
    public Optional<Participant> getUser(){
        return this.participants.stream()
                    .filter(Participant::isUser)
                    .findFirst();
    }
    
    
    @Transient
    public boolean isAnonymous(){
        return this.participants.stream()
                    .filter(Participant::isUser)
                    .filter( p -> Participant.ANNONYMUS_USER.equals(p.getName()) )
                    .findFirst()
                    .isPresent();
    }
    
    
    @Transient
    public Participant assignBot(){
        if(hasAssignedBot()){
           return getBot().get();
        }
        
        Participant bot = new Participant();
        bot.setType(ParticipantType.BOT);
        bot.setName("Bot");
        bot.setId(UUID.randomUUID().toString());
        participants.add(bot);
        return bot;
    }
    
    public boolean agentHasNotJoined(){
        return this.participants.stream()
                .filter(Participant::isAgent)
                .count() == 0;
    }
}
