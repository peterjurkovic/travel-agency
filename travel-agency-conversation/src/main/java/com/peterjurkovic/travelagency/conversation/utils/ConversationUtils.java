package com.peterjurkovic.travelagency.conversation.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;

@Component
public class ConversationUtils {

    
    
    
    @Autowired
    private ConversationRepository conversationRepository;
    
    public Optional<Conversation> conversationOf(SimpMessageHeaderAccessor headers){
        Optional<String> conversationId = StompUtils.getConversationId(headers.getDestination());
        if(  conversationId .isPresent() ){
            return conversationRepository.findById(conversationId.get());
        }
        return Optional.empty();
    }
    
    public Optional<Participant> participantOf(SimpMessageHeaderAccessor headers){
        Optional<Conversation> conversation = conversationOf(headers);
        if(  conversation .isPresent() ){
            String participantId = StompUtils.getParticipantId(headers);
            return conversation.get().findParticipantById(participantId);
        }
        return Optional.empty();
    }
    
    
    
    
    
}
