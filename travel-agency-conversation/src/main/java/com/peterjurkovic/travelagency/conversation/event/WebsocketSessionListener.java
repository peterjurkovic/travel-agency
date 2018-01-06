package com.peterjurkovic.travelagency.conversation.event;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.conversation.event.WebsocketSessionRepository.SessionConversations;

import com.peterjurkovic.travelagency.conversation.utils.ConversationUtils;
import com.peterjurkovic.travelagency.conversation.utils.StompUtils;



public class WebsocketSessionListener {

    private final Logger log = LoggerFactory.getLogger(getClass());
        
    @Autowired
    private WebsocketSessionRepository sessionRepository;
    
    @Autowired
    private ConversationUtils conversationUtils;
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
    @EventListener
    private void hadnleSubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        
        Optional<Participant> participant = conversationUtils.participantOf(headers);
         
        if(  participant.isPresent() ){
            Optional<String> conversatinoId = StompUtils.getConversationId(headers.getDestination());
            
            if(conversatinoId.isPresent()){
                sessionRepository.joinConversation(headers.getSessionId(), conversatinoId.get(), participant.get());
                notifyUserJoined(conversatinoId.get(), participant.get().getId());
                log.info("User subscribed {} conversatinoId {}", participant.get().getName(), conversatinoId.get());
            }
        }
    }
    
    @EventListener
    private void hadnleUnsubscribe(SessionUnsubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Optional<String> conversatinoId = StompUtils.getConversationId(headers.getDestination());
        if(  conversatinoId .isPresent() ){
          
            Optional<String> participantId = sessionRepository.leaveConversation(headers.getSessionId(), conversatinoId.get());
            
            if(participantId.isPresent()){
                notifyUserLeft(conversatinoId.get(), participantId.get());
                log.info("User unsubscribed conversatinoId {}", conversatinoId);
            }
        }
        
    }

    
    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
        Optional<SessionConversations> removeSession = sessionRepository.remove(event.getSessionId());
        
        if(removeSession.isPresent()){
            removeSession.get().getConversations().forEach( conversation -> {
                notifyUserLeft(conversation, removeSession.get().getParticipantId());
            });
            
            log.debug("User {} disconnected No conversations {}", 
                    removeSession.get().getParticipantId(), 
                    removeSession.get().getNumberOfConversations());
        }

    }
    
    
    private void notifyUserJoined(String conversatinoId, String participantId) {
        String destination = "/topic/participants/"+conversatinoId;
        messagingTemplate.convertAndSend(destination, ParticipantEvent.joined(participantId));
        log.debug("User {} joined {}", participantId, conversatinoId);
    }
    
    private void notifyUserLeft(String conversatinoId, String participantId) {
        String destination = "/topic/participants/"+conversatinoId;
        messagingTemplate.convertAndSend(destination, ParticipantEvent.left(participantId));
        log.debug("User {} left {}", participantId, conversatinoId);
    }
    
 
}
