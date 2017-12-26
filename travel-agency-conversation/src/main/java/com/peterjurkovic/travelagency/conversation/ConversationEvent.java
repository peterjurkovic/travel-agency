package com.peterjurkovic.travelagency.conversation;

import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;

public class ConversationEvent {

    public enum Type {
        CHAT_MESSAGE, USER_JOINED, USER_LEFT, AGENT_JOINED, AGENT_LEFT
    }

    private Type type;

    private Payload payload;

    public ConversationEvent(Type type, Payload payload){
        this.type = type;
        this.payload = payload;
    }
    
    public ConversationEvent(Type type){
        this.type = type;
    }
    
    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Payload getPayload() {
        return payload;
    }

    public void setPayload(Payload payload) {
        this.payload = payload;
    }

    public static class Payload {

        private Map<String, Object> properties = new HashMap<>();
        
        public Payload(){}
        
        public Payload(Map<String, Object> properties){
            this.properties.putAll(properties);
        }
        
        @JsonAnySetter
        public void setProperties(String name, Object value) {
            properties.put(name, value);
        }

        @JsonAnyGetter
        public Map<String, Object> getProperties() {
            return properties;
        }

    }
}
