package com.peterjurkovic.travelagency.conversation;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

import com.peterjurkovic.travelagency.common.model.Conversation;

@Repository
public interface ConversationRepository extends ReactiveCrudRepository<Conversation, String>{

}
