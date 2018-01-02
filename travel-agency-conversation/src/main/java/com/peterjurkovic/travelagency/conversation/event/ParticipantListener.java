package com.peterjurkovic.travelagency.conversation.event;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;


public class ParticipantListener {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private SimpMessagingTemplate messagingTemplate;
    
    @EventListener
    private void handleSessionConnected(SessionConnectEvent event) {

        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        
        log.info( headers.getDestination());

//        LoginEvent loginEvent = new LoginEvent(username);
//        messagingTemplate.convertAndSend(loginDestination, loginEvent);
//        
//        // We store the session as we need to be idempotent in the disconnect event processing
//        participantRepository.add(headers.getSessionId(), loginEvent);
    }
    
    @EventListener
    private void handleSessionDisconnect(SessionDisconnectEvent event) {
       
    }
    
    @EventListener
    private void hadnleSubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());

        log.info("hadnleSubscribe"+ headers.getDestination());
    }
    
    @EventListener
    private void hadnleUnsubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        log.info( "hadnleUnsubscribe"+headers.getDestination());
    }
}
