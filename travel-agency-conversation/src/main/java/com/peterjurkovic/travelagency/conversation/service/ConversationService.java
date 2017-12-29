package com.peterjurkovic.travelagency.conversation.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;

@Service
public class ConversationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    private final ConversationRepository repository;
    
    @Autowired
    public ConversationService(ConversationRepository repository){
        this.repository = repository;
    }
    
    
    public Conversation inicateConversation(Participant creator){
        Conversation conversation = new Conversation();
        conversation.addParticipant(creator);
        repository.save(conversation);
        log.info("Conversation iniciated: {}" , conversation );
        return conversation;
    }
}
