package com.peterjurkovic.travelagency.conversation.model;

import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.model.ParticipantType;

public class ParticipantState {

    private final String id;
    private final String name;
    private final String phoneNumber;
    private final ParticipantType type;
    private final boolean active;

    public ParticipantState(Participant participant, boolean isActive) {
        this.id = participant.getId();
        this.name = participant.getName();
        this.phoneNumber = participant.getPhoneNumber();
        this.type = participant.getType();
        this.active = isActive;
    }

    public boolean isActive() {
        return active;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public ParticipantType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ParticipantState [id=" + id + ", name=" + name + ", phoneNumber=" + phoneNumber + ", type=" + type + ", active=" + active + "]";
    }

}
