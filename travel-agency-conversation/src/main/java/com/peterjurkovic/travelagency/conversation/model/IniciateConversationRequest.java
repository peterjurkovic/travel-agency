package com.peterjurkovic.travelagency.conversation.model;

import javax.validation.constraints.NotNull;

public class IniciateConversationRequest {

    @NotNull
    private String participantId;

    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    @Override
    public String toString() {
        return "[participantId=" + participantId + "]";
    }
    
    
    
}
