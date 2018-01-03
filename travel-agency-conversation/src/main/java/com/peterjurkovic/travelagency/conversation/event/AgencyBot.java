package com.peterjurkovic.travelagency.conversation.event;

import java.time.Instant;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionSubscribeEvent;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.conversation.event.MessageBuilder.MessageType;
import com.peterjurkovic.travelagency.conversation.model.CreateMessage;
import com.peterjurkovic.travelagency.conversation.service.ConversationService;
import com.peterjurkovic.travelagency.conversation.utils.ConversationUtils;

@Component
public class AgencyBot {
    
    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired 
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ConversationService conversationService;
    
    @Autowired
    private ConversationUtils conversationUtils;
    
    @Autowired
    private ConversationRepository conversationRepository;
    
    @EventListener
    public void handleNewSubscription(SessionSubscribeEvent event){
        SimpMessageHeaderAccessor headers = SimpMessageHeaderAccessor.wrap(event.getMessage());
        
        Optional<Conversation> conversation = conversationUtils.conversationOf(headers);
        if(  conversation.isPresent() && shouldAskForPhoneNumber(conversation.get()) ){
            
            Optional<Participant> participant = conversationUtils.participantOf(headers);
            if(participant.isPresent() && participant.get().isUser()){
                Participant bot = conversation.get().assignBot();
               
                conversationRepository.save(conversation.get());
                
                String destination = "/topic/chat/" + conversation.get().getId();
                
                CreateMessage message = MessageBuilder.message(MessageType.PHONE_NUMBER_REQUEST)
                                          .withAuthor( bot )
                                          .withConversation( conversation.get() )
                                          .build();
                
                ConversationMessage conversationMessage = conversationService.create(message);
                messagingTemplate.convertAndSend(destination, conversationMessage);
                log.info("Phone number request sent: {}" , conversationMessage);
            }
                        
        }
    }
    
    @SubscribeMapping("/topic/bot/{conversationId}")
    public void handleUserMessages(
            @Payload ConversationMessage message,
            @DestinationVariable String conversationId){
        
        log.info("User Message {}", message);
        
    }
    
    public boolean shouldAskForPhoneNumber(Conversation conversation){
        if(conversation.hasPhoneNumberAssigned()){
            return false;
        }
        
        // if an agent join a chat its up to him to communicate
        if(conversation.hasAssignedBot()){
            return false;
        }
        return conversationService.getMessages(conversation, Instant.now()).size() == 0;
    }
}
