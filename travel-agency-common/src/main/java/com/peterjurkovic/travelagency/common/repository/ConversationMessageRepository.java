package com.peterjurkovic.travelagency.common.repository;

import java.time.Instant;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;

@Repository
public interface ConversationMessageRepository extends PagingAndSortingRepository<ConversationMessage, String> {

    
    Page<ConversationMessage> findByConversationAndCreatedBefore(Conversation conversation, Instant createdBefore, Pageable pageable);
    
    Page<ConversationMessage> findByConversation(Conversation conversation, Pageable pageable);
}

