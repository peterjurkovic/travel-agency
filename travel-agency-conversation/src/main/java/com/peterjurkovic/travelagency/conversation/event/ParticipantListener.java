package com.peterjurkovic.travelagency.conversation.event;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;
import org.springframework.web.socket.messaging.SessionUnsubscribeEvent;

import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.conversation.repository.ParticipantRepository;
import com.peterjurkovic.travelagency.conversation.repository.ParticipantRepository.UnmodifiableParticipant;
import com.peterjurkovic.travelagency.conversation.utils.ConversationUtils;
import com.peterjurkovic.travelagency.conversation.utils.StompUtils;


public class ParticipantListener {

    private final Logger log = LoggerFactory.getLogger(getClass());
        
    @Autowired
    private ParticipantRepository participantRepository;
    
    @Autowired
    private ConversationUtils conversationUtils;
    
    @EventListener
    private void hadnleSubscribe(SessionSubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        
        Optional<Participant> participant = conversationUtils.participantOf(headers);
         
        if(  participant.isPresent() ){
            Optional<String> conversatinoId = StompUtils.getConversationId(headers.getDestination());
            String session = headers.getSessionId();
            UnmodifiableParticipant user = new UnmodifiableParticipant(session, participant.get());
            participantRepository.add(conversatinoId.get(), user);
            log.info("User subscribed {} conversatinoId {}", user, conversatinoId);
        }
    }
    
    @EventListener
    private void hadnleUnsubscribe(SessionUnsubscribeEvent event) {
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        Optional<String> conversatinoId = StompUtils.getConversationId(headers.getDestination());
        if(  conversatinoId .isPresent() ){
          
            String session = headers.getSessionId();

            participantRepository.remove(conversatinoId.get(), session);
            log.info("User unsubscribed conversatinoId {}", conversatinoId);
        }
        
    }
}
