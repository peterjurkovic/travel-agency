package com.peterjurkovic.travelagency.conversation.event;


import org.springframework.core.Ordered;

import com.peterjurkovic.travelagency.common.model.ConversationMessage;

public interface BotAnswer extends Ordered {

   boolean tryAnswer(ConversationMessage message);
}
