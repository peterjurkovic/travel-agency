package com.peterjurkovic.travelagency.conversation.web;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.http.MediaType;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.common.model.InboundSmsMessage;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.conversation.event.WebsocketSessionRepository;
import com.peterjurkovic.travelagency.conversation.event.UserMessageCreatedEvent;
import com.peterjurkovic.travelagency.conversation.model.CreateMessage;
import com.peterjurkovic.travelagency.conversation.model.IniciateConversationRequest;
import com.peterjurkovic.travelagency.conversation.model.ParticipantState;
import com.peterjurkovic.travelagency.conversation.service.ConversationService;
import com.peterjurkovic.travelagency.conversation.sms.SmsConversationService;

@CrossOrigin("*")
@Controller
public class ConversationController {

    private final Logger log = LoggerFactory.getLogger(getClass());
         
    @Autowired
    private ConversationService conversationService; 
    
    @Autowired
    private ConversationRepository conversationRepository; 
    
    @Autowired
    private WebsocketSessionRepository sessionRepository;
    
    @Autowired
    private SmsConversationService smsConversationService;
    
    @Autowired
    private ApplicationEventPublisher publisher;
    
    @PostMapping("/conversations")
    @ResponseBody
    public Conversation create(@Valid @RequestBody IniciateConversationRequest request){

        Conversation conversation = conversationService.inicateConversation( request.toParticipant() );
        
        log.info("Conversation created {}", conversation);
        
        return conversation;
    }
    
    @PutMapping(value = "/conversations/{id}/join", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public Participant join(
            @RequestBody Participant user, 
            @PathVariable String id){

        Participant participant = conversationService.joinConversation(user, id);
        
        log.info("User {} joined conversation {}", participant, id);
        
        return participant;
    }
   
    
    @GetMapping(value = "/conversations/{id}/messages", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<ConversationMessage> getMessages(
            @PathVariable String id, 
            @RequestParam(required = false) Instant createdBefore){
        log.debug("Seachring for ID {} createdBefore {}", id , createdBefore);
        if(createdBefore == null) createdBefore = Instant.now();
        return conversationService.getMessages(id, createdBefore);
    }
    
    @ResponseBody
    @PostMapping(value = "/inbound/sms", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void smsInboundMessageHandler(@RequestBody InboundSmsMessage mesage){
        smsConversationService.saveAndBroadcast(mesage); 
    }
    
    @SubscribeMapping("/participants/{id}")
    public List<ParticipantState> getParticipants(@DestinationVariable String id){
        return loadOnlineParticipants(id);
    }
    
    
    @SubscribeMapping("/conversations")
    public List<Conversation> getConversations(){
        PageRequest req = PageRequest.of(0, 30, Direction.DESC, "date");
        return conversationRepository.findAll(req).getContent();
    }
    
    

    private List<ParticipantState> loadOnlineParticipants(String id) {
        log.debug("@SubscribeMapping getParticipants {}", id);
        Optional<Conversation> conversation = conversationRepository.findById(id);
        Set<String> onlineParticipantsIs = sessionRepository.getOnlineParticipantsInConversation(id);
        if(conversation.isPresent()){
            return conversation.get().getParticipants()
                        .stream()
                        .map( p -> new ParticipantState(p, onlineParticipantsIs.contains(p.getId()) ))
                        .collect(Collectors.toList());

        }
        return Collections.emptyList();
    }
    
    @MessageMapping("/chat/{conversationId}")
    @SendTo("/topic/chat/{conversationId}")
    public ConversationMessage createMesage(
            @Payload CreateMessage message,
            @DestinationVariable String conversationId) {

        log.info("handleMessage {}", message);
        
        
        ConversationMessage conversationMessage = conversationService.create( message );
        if(conversationMessage.isUserMessage()){
            publisher.publishEvent(new UserMessageCreatedEvent(conversationMessage));
        }
        return conversationMessage;
    }

    
}
