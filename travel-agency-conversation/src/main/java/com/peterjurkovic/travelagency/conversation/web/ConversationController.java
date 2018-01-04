package com.peterjurkovic.travelagency.conversation.web;

import java.time.Instant;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.conversation.model.CreateMessage;
import com.peterjurkovic.travelagency.conversation.model.IniciateConversationRequest;
import com.peterjurkovic.travelagency.conversation.service.ConversationService;

@CrossOrigin
@Controller
public class ConversationController {

    private final Logger log = LoggerFactory.getLogger(getClass());
         
    @Autowired
    private ConversationService conversationService; 
    
    @Autowired 
    private SimpMessagingTemplate messagingTemplate;
    
    @PostMapping("/conversations")
    @ResponseBody
    public Conversation create(@Valid @RequestBody IniciateConversationRequest request){

        Conversation conversation = conversationService.inicateConversation( request.toParticipant() );
        
        log.info("Conversation created {}", conversation);
        
        return conversation;
    }
    
    @GetMapping("/conversations/{id}/messages")
    @ResponseBody
    public List<ConversationMessage> getMessages(
            @PathVariable String id, 
            @RequestParam(required = false) Instant createdBefore){
        log.debug("Seachring for ID {} createdBefore {}", id , createdBefore);
        if(createdBefore == null) createdBefore = Instant.now();
        return conversationService.getMessages(id, createdBefore);
    }
    
  
    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/chat/{conversationId}")
    public ConversationMessage createMesage(
            @Payload CreateMessage message,
            @DestinationVariable String conversationId) {

        log.info("handleMessage {}", message);
        
        ConversationMessage conversationMessage = conversationService.create(message);
//        if(conversationMessage.isUserMessage()){
//            messagingTemplate.convertAndSend("/topic/bot/{conversationId}/bot", conversationMessage);
//        }
        return conversationMessage;
    }
    
    @SubscribeMapping("/topic/chat/{conversationId}")
    public void handleUserMessages(
            @Payload ConversationMessage message,
            @DestinationVariable String conversationId){
        
        log.info("User Message {}", message);
        
    }
    
    
}
