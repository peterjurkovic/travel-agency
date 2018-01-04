package com.peterjurkovic.travelagency.conversation.service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.repository.ConversationMessageRepository;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.conversation.model.CreateMessage; 

@Service
public class ConversationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final ConversationRepository repository;
    private final ConversationMessageRepository conversationMessageRepository;
    
    @Autowired
    public ConversationService(ConversationRepository repository,
            ConversationMessageRepository conversationMessageRepository){
        this.repository = repository;
        this.conversationMessageRepository = conversationMessageRepository;
    }
    
    public List<ConversationMessage> getMessages(String conversatinoId, Instant createdBefore){
        Conversation conversation = getConversation(conversatinoId);
        return getMessages(conversation, createdBefore);
    }
    
    public List<ConversationMessage> getMessages(Conversation conversation, Instant createdBefore){
        Pageable pageable = PageRequest.of(0, 15, Direction.ASC, "created");
        Page<ConversationMessage> page = conversationMessageRepository.findByConversationAndCreatedBefore(conversation, createdBefore, pageable);
        page.getContent().forEach( msg -> msg.setConversation(conversation));
        return page.getContent();
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
}
