package com.peterjurkovic.travelagency.common.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.peterjurkovic.travelagency.common.model.Conversation;

@Repository
public interface ConversationRepository extends PagingAndSortingRepository<Conversation, String> {

}
