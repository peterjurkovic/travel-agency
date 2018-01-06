package com.peterjurkovic.travelagency.conversation.event;

public class ParticipantEvent {

    private enum Type {
        JOINED, LEFT
    }

    private final String id;
    private final Type type;

    private ParticipantEvent(String value, Type type) {
        this.type = type;
        this.id = value;
    }

    public String getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public static ParticipantEvent joined(String participantId) {
        return new ParticipantEvent(participantId, Type.JOINED);
    }

    public static ParticipantEvent left(String participantId) {
        return new ParticipantEvent(participantId, Type.LEFT);
    }
}
