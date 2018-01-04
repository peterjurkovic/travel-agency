package com.peterjurkovic.travelagency.conversation.utils;

import java.util.Optional;

import org.springframework.messaging.simp.SimpMessageHeaderAccessor;

public abstract class StompUtils {

    private final static String PARTICIPANT_ID_HEADER =  "participantId";
    
    public static Optional<String> getConversationId(String destination){
        if(destination != null && destination.startsWith("/topic/chat/")){
            String[] parts = destination.split("/");
            if(parts.length == 4){
                return Optional.ofNullable(parts[3]);
            }
        }
        return Optional.empty();
    }
    
    public static String getParticipantId(SimpMessageHeaderAccessor headers){
        return (String) headers.getFirstNativeHeader(PARTICIPANT_ID_HEADER);
    }
}
