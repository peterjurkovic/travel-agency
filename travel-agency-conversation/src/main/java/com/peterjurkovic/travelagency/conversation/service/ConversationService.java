package com.peterjurkovic.travelagency.conversation.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.common.model.ConversationMessage.Type;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.repository.ConversationMessageRepository;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.conversation.event.ParticipantEvent;
import com.peterjurkovic.travelagency.conversation.event.WebsocketSessionRepository;
import com.peterjurkovic.travelagency.conversation.model.CreateMessage;
import com.peterjurkovic.travelagency.conversation.sms.SmsConversationService;


@Service
public class ConversationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final ConversationRepository repository;
    private final ConversationMessageRepository conversationMessageRepository;
    private final SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private WebsocketSessionRepository sessionRepository;
    
    @Autowired
    private SmsConversationService smsConversationService;
    
    @Autowired
    public ConversationService(ConversationRepository repository,
           ConversationMessageRepository conversationMessageRepository,
           SimpMessagingTemplate messagingTemplate){
        this.repository = repository;
        this.conversationMessageRepository = conversationMessageRepository;
        this.messagingTemplate = messagingTemplate;
    }
    
    public List<ConversationMessage> getMessages(String conversatinoId, Instant createdBefore){
        Conversation conversation = getConversation(conversatinoId);
        return getMessages(conversation, createdBefore);
    }
    
    public List<ConversationMessage> getLastMessages(Conversation conversation){
        return getMessages(conversation, Instant.now() );
    }
    
    public List<ConversationMessage> getMessages(Conversation conversation, Instant createdBefore){
        Pageable pageable = PageRequest.of(0, 15, Direction.ASC, "created");
        Page<ConversationMessage> page = conversationMessageRepository.findByConversationAndCreatedBefore(conversation, createdBefore, pageable);
        page.getContent().forEach( msg -> msg.setConversation(conversation));
        return page.getContent();
    }
    
    public Participant joinConversation(Participant participant, String id){
        Conversation conversation = getConversation(id);
        if(participant.getId() == null){
            participant.setId(UUID.randomUUID().toString());
        }
        if( ! conversation.getParticipants().contains(participant) ){
            conversation.addParticipant(participant);
            repository.save(conversation);
            
            
            messagingTemplate.convertAndSend("/topic/participants/"+id, ParticipantEvent.joined(participant.getId()));
            
            messagingTemplate.convertAndSend("/topic/chat/"+id, joinConversationMessage(conversation, participant));
            
            log.info("Participant joined conversation {} " , id);
        }else{
            log.warn("Participant already joned conversation {} " , id);
        }
        return participant;
    }
    
    public Conversation inicateConversation(Participant creator){
        Conversation conversation = new Conversation();
        conversation.addParticipant(creator);
        repository.save(conversation);
        log.info("Conversation iniciated: {}" , conversation );
        return conversation;
    }
    
    public ConversationMessage create(CreateMessage message){
        Conversation conversation = getConversation(message.getConversationId());
        ConversationMessage conversationMessage = message.toConversationMessage(conversation);
        
        if( ! sessionRepository.isParticipantOnline(conversationMessage.getParticipantId())){
            conversationMessage.setType(Type.SMS);
            smsConversationService.sendSms(conversationMessage);
        }
        
        conversationMessageRepository.save(conversationMessage);
        log.info("New message created {}" , conversationMessage.getId());
        return conversationMessage;
    }
    
    
    private Conversation getConversation(String conversatinoId){
        Optional<Conversation> conversation = repository.findById( conversatinoId );
        if(conversation.isPresent()){
            return conversation.get();
        }
        log.warn("No conversation found ID: {}" , conversatinoId);
        throw new ConversationNotFoundException("Conversation was not found");
    }
    
    
    private ConversationMessage joinConversationMessage(Conversation conversation, Participant participant){
        ConversationMessage message = new ConversationMessage(conversation, participant);
        message.setType(Type.EVENT);
        message.setContent(participant.getName() + " has joined");
        conversationMessageRepository.save(message);
        return message;
    }
}
