package com.peterjurkovic.travelagency.admin.conversation;

import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.common.repository.ConversationMessageRepository;

@Component
public class AdminConversationService {

    @Autowired
    private ConversationMessageRepository repository;
    
    public List<ConversationMessage> getLastMessages(Conversation conversation){
        return getMessages(conversation, Instant.now() );
    }
    
    public List<ConversationMessage> getMessages(Conversation conversation, Instant createdBefore){
        Pageable pageable = PageRequest.of(0, 30, Direction.ASC, "created");
        Page<ConversationMessage> page = repository.findByConversationAndCreatedBefore(conversation, createdBefore, pageable);
        page.getContent().forEach( msg -> msg.setConversation(conversation));
        return page.getContent();
    }
}
