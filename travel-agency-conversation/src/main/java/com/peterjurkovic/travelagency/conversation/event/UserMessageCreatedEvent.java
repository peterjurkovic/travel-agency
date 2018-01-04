package com.peterjurkovic.travelagency.conversation.event;

import com.peterjurkovic.travelagency.common.model.ConversationMessage;

public class UserMessageCreatedEvent  {

    private final ConversationMessage message;

    public UserMessageCreatedEvent(ConversationMessage message) {
        this.message = message;
       
    }

    public ConversationMessage getMessage() {
        return message;
    }
    
    
    

}
