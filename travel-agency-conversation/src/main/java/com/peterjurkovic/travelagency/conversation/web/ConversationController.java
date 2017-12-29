package com.peterjurkovic.travelagency.conversation.web;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.conversation.model.ConversationMessage;
import com.peterjurkovic.travelagency.conversation.model.IniciateConversationRequest;
import com.peterjurkovic.travelagency.conversation.service.ConversationService;

@CrossOrigin
@Controller
public class ConversationController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired private SimpMessagingTemplate simpMessagingTemplate;
    
    @Autowired
    private ConversationService conversationService; 
    
    @PostMapping("/conversations")
    @ResponseBody
    public Conversation create(@Valid @RequestBody IniciateConversationRequest request){

        Conversation conversation = conversationService.inicateConversation( Participant.user(request.getParticipantId() ));
        
        log.info("Conversation created {}", conversation);
        
        return conversation;
    }
    
  
    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/chat/{conversationId}")
    public ConversationMessage createMesage(
            @Payload ConversationMessage message,
            @DestinationVariable String conversationId) {

        log.info("handleMessage {}", message);
        
        return message;
    }
    
}
