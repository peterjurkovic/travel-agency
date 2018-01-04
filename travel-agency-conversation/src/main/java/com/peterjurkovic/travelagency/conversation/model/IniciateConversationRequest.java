package com.peterjurkovic.travelagency.conversation.model;


import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.model.ParticipantType;

public class IniciateConversationRequest {

     
    private String participantName;
    
    @NotNull
    private ParticipantType participantType = ParticipantType.USER;

    public String getParticipantName() {
        return participantName;
    }

    public void setParticipantName(String participantName) {
        this.participantName = participantName;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }

    public void setParticipantType(ParticipantType participantType) {
        this.participantType = participantType;
    }
    
    public Participant toParticipant(){
        Participant participant = new Participant();
        participant.setType(participantType);
        participant.setId(UUID.randomUUID().toString());
        participant.setName(participantName == null ? Participant.ANNONYMUS_USER : participantName );
        return participant;
    }
    @Override
    public String toString() {
        return "[participantName=" + participantName + ", participantType=" + participantType + "]";
    }
 
    

}
